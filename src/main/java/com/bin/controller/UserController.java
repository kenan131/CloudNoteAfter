package com.bin.controller;

import com.bin.model.dto.ResResponse;
import com.bin.model.Enum.ResStatus;
import com.bin.model.dto.UserDto;
import com.bin.service.imple.UserService;
import com.bin.util.Md5Util;
import com.bin.util.RedisKeyUtil;
import com.google.code.kaptcha.Producer;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("user")
@CrossOrigin
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private Producer kaptchaProducer;

    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping("login")
    public ResResponse login(@RequestBody UserDto userDto){
        ResResponse res;
        Map<String, String> ans = userService.login(userDto);
        if(ResStatus.ERROR.getType().equals(ans.get("type"))){
            res=new ResResponse(ResStatus.ERROR.getType(),ans.get("message"));
        }else{
            res=new ResResponse(ResStatus.Success.getType(),ans.get("message"));
        }
        return res;
    }

    @PostMapping("sendEmail")
    public ResResponse sendEmail(@RequestBody UserDto userDto){
        Map<String, String> ans = userService.sendEmail(userDto);
        ResResponse res;
        if(ResStatus.ERROR.getType().equals(ans.get("type"))){
            res=new ResResponse(ResStatus.ERROR.getType(),ans.get("message"));
        }
        else{
            res=new ResResponse(ResStatus.Success.getType(),ans.get("message"));
        }
        return res;
    }
    //注册账号
    @PostMapping("active")
    public ResResponse active(@RequestBody UserDto userDto){
        Map<String, String> ans = userService.active(userDto);
        ResResponse res;
        if(ResStatus.ERROR.getType().equals(ans.get("type"))){
            res=new ResResponse(ResStatus.ERROR.getType(),ans.get("message"));
        }
        else{
            res=new ResResponse(ResStatus.Success.getType(),ans.get("message"));
        }
        return res;
    }
    //获取验证码
    @RequestMapping(path = "/captcha", method = RequestMethod.GET)
    public void getKaptcha(HttpServletResponse response,@PathParam("random") String random) {
        // 生成验证码
        String text = kaptchaProducer.createText();
        BufferedImage image = kaptchaProducer.createImage(text);

        //将验证码存入redis
        String redisKey= RedisKeyUtil.getLoginCaptcha(random);
        redisTemplate.opsForValue().set(redisKey,text,60, TimeUnit.SECONDS);

        // 将突图片输出给浏览器
        response.setContentType("image/png");
        try {
            OutputStream os = response.getOutputStream();
            ImageIO.write(image, "png", os);
        } catch (IOException e) {
            logger.error("响应验证码失败:" + e.getMessage());
            System.out.println("验证码错误");
        }
    }

    //重置密码验证
    @PostMapping("rePasswordCaptcha")
    public ResResponse rePasswordCaptcha(@RequestBody UserDto userDto){
        Map<String, String> ans = userService.rePasswordCaptcha(userDto);
        ResResponse res;
        if(ResStatus.ERROR.getType().equals(ans.get("type"))){
            res=new ResResponse(ResStatus.ERROR.getType(),ans.get("message"));
        }
        else{
            res=new ResResponse(ResStatus.Success.getType(),ans.get("message"));
        }
        return res;
    }

    //更新密码
    @PostMapping("rePassword")
    public ResResponse rePassword(@RequestBody UserDto userDto){
        Map<String, String> ans = userService.rePassword(userDto);
        ResResponse res;
        if(ResStatus.ERROR.getType().equals(ans.get("type"))){
            res=new ResResponse(ResStatus.ERROR.getType(),ans.get("message"));
        }
        else{
            res=new ResResponse(ResStatus.Success.getType(),ans.get("message"));
        }
        return res;
    }

    @PostMapping("checkCaptchaApi")
    public ResResponse checkCaptchaApi(@RequestBody UserDto userDto){
        Map<String, String> ans = userService.checkCaptchaApi(userDto);
        ResResponse res;
        if(ResStatus.ERROR.getType().equals(ans.get("type"))){
            res=new ResResponse(ResStatus.ERROR.getType(),ans.get("message"));
        }
        else{
            res=new ResResponse(ResStatus.Success.getType(),ans.get("message"));
        }
        return res;
    }
    @GetMapping("getUserName")
    public ResResponse getUserName(){
        Object userName = userService.getUserName();
        ResResponse res = new ResResponse("", "", userName);
        return res;
    }

    @GetMapping ("loginOut")
    public void loginOut(HttpServletRequest request){
        String token = request.getHeader("token");
        if(!StringUtils.isBlank(token)){
            String key = RedisKeyUtil.getUserTicket(token);
            redisTemplate.delete(key);
        }
    }
}
