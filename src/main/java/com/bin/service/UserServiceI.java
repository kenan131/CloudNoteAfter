package com.bin.service;

import com.bin.model.dto.UserDto;

import java.util.Map;

/**
 * @author: bin.jiang
 * @date: 2023/3/1 13:53
 **/

public interface UserServiceI {
    //登录
    public Map<String,String> login(UserDto userDto);
    public Map<String,String> register(String account,String password,String email);
    //获取眼验证码
    public Map<String,String> sendEmail(UserDto dto);
    //激活
    public Map<String,String > active(UserDto dto);
    public Map<String,String> rePasswordCaptcha(UserDto userDto);
    public Map<String,String> rePassword(UserDto userDto);
    public Map<String,String> checkCaptchaApi(UserDto userDto);

    public Object getUserName();
}
