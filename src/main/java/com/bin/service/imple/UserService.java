package com.bin.service.imple;

import com.bin.model.Enum.ResStatus;
import com.bin.model.Enum.UserStatus;
import com.bin.model.dto.UserDto;
import com.bin.model.entity.User;
import com.bin.model.mapper.UserMapper;
import com.bin.service.UserServiceI;
import com.bin.util.HostHolder;
import com.bin.util.MailClient;
import com.bin.util.Md5Util;
import com.bin.util.RedisKeyUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class UserService implements UserServiceI {
    @Autowired(required = false)
    private UserMapper userMapper;

    @Autowired
    private MailClient mailClient;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private HostHolder hostHolder;

    @Transactional
    @Override
    public Map<String,String> login(UserDto userDto){
        Map<String,String> res = new HashMap();
        res.put("type",ResStatus.ERROR.getType());
        if(userDto.getAccount() ==null || userDto.getPassword()==null){
            res.put("message","账号或者密码为空！");
            return res;
        }
        User user = userMapper.selectByAccount(userDto.getAccount());
        if (user==null){
            res.put("message","用户不存在!");
            return res;
        }
        if(user.getActive() == UserStatus.UNACTIVE.getCode()){
            res.put("message","该账号没有激活,请激活后再登录。");
            return res;
        }
        String captchaKey = RedisKeyUtil.getLoginCaptcha(userDto.getRandom());
        String redisCaptcha = (String)redisTemplate.opsForValue().get(captchaKey);
        if(redisCaptcha==null){
            res.put("message","验证码过期！");
            return res;
        }
        if(!userDto.getCaptcha().equals(redisCaptcha)){
            res.put("message","验证码错误！");
        }
        if(Md5Util.md5(userDto.getPassword()+user.getSalt()).equals(user.getPassword())){
            res.put("type",ResStatus.Success.getType());
            String ticket = Md5Util.generateUUID();
            String key = RedisKeyUtil.getUserTicket(ticket);
            redisTemplate.opsForValue().set(key,user,24,TimeUnit.HOURS);//设置24小时过期
            res.put("message",ticket);
        }else{
            res.put("message","密码错误!");
        }
        return res;
    }

    @Transactional
    @Override
    public Map<String,String> register(String account,String password,String email){
        Map res = new HashMap<String,String>();
        User user = userMapper.selectByAccount(account);
        res.put("type","E");
        if(user!=null&&user.getActive()==UserStatus.ACTIVE.getCode()){
            res.put("message","已存在该账号！");
            return res;
        }
        user=userMapper.selectByEmail(email);
        if(user!=null&&user.getActive()==UserStatus.ACTIVE.getCode()){
            res.put("message","该邮箱已被其他账号注册！");
            return res;
        }
        res.put("type","S");
        if(user == null){
            String salt = Md5Util.generateUUID().substring(0, 5);
            password=Md5Util.md5(password+salt);
            user=new User(account,password,email,salt,UserStatus.UNADMIN.getCode(), UserStatus.UNACTIVE.getCode());
            int cnt = userMapper.insertUser(user);
            if(cnt!=1){
                res.put("message","插入失败！");
                return res;
            }
            res.put("message","插入成功！");
            return res;
        }
        else{
            String salt = user.getSalt();
            password=Md5Util.md5(password+salt);
            int cnt = userMapper.updatePassword(user.getId(),password);//最新的登录密码
        }
        res.put("message","存在账号，但未激活！");
        return res;
    }
    @Override
    public Map<String,String> sendEmail(UserDto dto){
        Map<String, String> res = register(dto.getAccount(), dto.getPassword(), dto.getEmail());
        if(ResStatus.ERROR.getType().equals(res.get("type"))){
            return res;
        }
        String captcha = Md5Util.generateUUID().substring(0, 6);
        String str = "验证码为：   "+captcha+"       5分钟内输入有效。（请及时输入）";
        try{
            mailClient.sendMail(dto.getEmail(),"云笔记系统注册用户",str);
        }catch (Exception e){
            res.put("message","验证码发送失败，请重试！");
            return res;
        }
        redisTemplate.opsForValue().set(RedisKeyUtil.getRegisterCaptcha(dto.getAccount()),captcha,3, TimeUnit.MINUTES);//5分钟验证码
        res.put("type",ResStatus.Success.getType());
        res.put("message","发送验证码成功！");
        return res;
    }
    @Override
    public Map<String,String > active(UserDto dto){
        Map<String ,String > res = new HashMap<>();
        res.put("type",ResStatus.ERROR.getType());
        if(dto.getAccount()==null || dto.getPassword() == null){
            res.put("message","账号或者密码为空！");
            return res;
        }
        if(dto.getEmail() == null || dto.getCaptcha() == null){
            res.put("message","邮箱为空或者验证码为空！");
            return res;
        }
        User user = userMapper.selectByAccount(dto.getAccount());
        if(user==null){
            res.put("message","该账号["+dto.getAccount()+"]未注册成功！");
            return res;
        }
        String redisKey = RedisKeyUtil.getRegisterCaptcha(dto.getAccount());
        String captcha = (String) redisTemplate.opsForValue().get(redisKey);
        if(captcha==null){
            res.put("message","验证码已失效！");
            return res;
        }
        if(!captcha.equals(dto.getCaptcha())){
            res.put("message","验证码错误！");
            return res;
        }
        userMapper.updateActive(user.getId(),UserStatus.ACTIVE.getCode());
        res.put("type",ResStatus.Success.getType());
        res.put("message","注册成功！");
        return res;
    }
    @Override
    public Map<String,String> rePasswordCaptcha(UserDto userDto){
        Map<String,String> res = new HashMap();
        res.put("type",ResStatus.ERROR.getType());
        if(StringUtils.isBlank(userDto.getAccount()) || StringUtils.isBlank(userDto.getEmail())){
            res.put("message","账号或者邮箱未输入。");
            return res;
        }
        User user = userMapper.selectByAccount(userDto.getAccount());
        if(user==null){
            res.put("message","账号输入错误！");
            return res;
        }
        if(!user.getEmail().equals(userDto.getEmail())){
            res.put("message","输入的账号和邮箱不是对应注册关系。");
            return res;
        }
        String captcha = Md5Util.generateUUID().substring(0, 6);
        String str = "验证码为：   "+captcha+"       5分钟内输入有效。（请及时输入）";
        try{
            mailClient.sendMail(userDto.getEmail(),"云笔记系统重置密码",str);
        }catch (Exception e){
            res.put("message","验证码发送失败，请重试！");
            return res;
        }
        redisTemplate.opsForValue().set(RedisKeyUtil.getRePasswordCaptcha(userDto.getAccount()),captcha,3, TimeUnit.MINUTES);//5分钟验证码
        res.put("type",ResStatus.Success.getType());
        res.put("message","发送验证码成功！");
        return res;
    }
    @Override
    public Map<String,String> rePassword(UserDto userDto){
        Map<String,String> res = new HashMap();
        res.put("type",ResStatus.ERROR.getType());
        if(StringUtils.isBlank(userDto.getAccount())||StringUtils.isBlank(userDto.getPassword())){
            res.put("message","参数缺失，请重试。");
            return res;
        }
        User user = userMapper.selectByAccount(userDto.getAccount());
        String password = Md5Util.md5(userDto.getPassword() + user.getSalt() );
        int cnt = userMapper.updatePassword(user.getId(), password);
        if(cnt==0){
            res.put("message","更新失败，请重试。");
            return res;
        }
        res.put("type",ResStatus.Success.getType());
        res.put("message","密码重置成功！");
        return res;
    }
    @Override
    public Map<String,String> checkCaptchaApi(UserDto userDto){
        Map<String,String> res = new HashMap();
        res.put("type",ResStatus.ERROR.getType());
        if(StringUtils.isBlank(userDto.getAccount()) || StringUtils.isBlank(userDto.getEmail())){
            res.put("message","账号或者邮箱未输入。");
            return res;
        }
        if(StringUtils.isBlank(userDto.getCaptcha())){
            res.put("message","验证码为空，请重试。");
            return res;
        }
        User user = userMapper.selectByAccount(userDto.getAccount());
        if(!userDto.getEmail().equals(userDto.getEmail())){
            res.put("message","输入的账号和邮箱不是对应注册关系。");
            return res;
        }
        String rePasswordCaptcha = RedisKeyUtil.getRePasswordCaptcha(user.getAccount());
        String captcha = (String) redisTemplate.opsForValue().get(rePasswordCaptcha);
        if(captcha == null)
        {
            res.put("message","验证码过期，请重试。");
            return res;
        }
        if(!captcha.equals(userDto.getCaptcha())){
            res.put("message","验证码错误。");
            return res;
        }
        res.put("type",ResStatus.Success.getType());
        res.put("message","验证成功！");
        return res;
    }

    @Override
    public Object getUserName() {
        User user = hostHolder.getUser();
        if(user!=null){
            return user.getAccount();
        }
        return null;
    }

    public User getUserByTicket(String ticket){
        String key = RedisKeyUtil.getUserTicket(ticket);
        User user = (User)redisTemplate.opsForValue().get(key);
        return user;
    }
}
