package com.bin.util;

/**
 * @author: bin.jiang
 * @date: 2023/2/28 15:00
 **/

public class RedisKeyUtil {
    //注册验证码
    public static String getRegisterCaptcha(String account){
        String res="register:"+account;
        return res;
    }
    //登录验证码
    public static String getLoginCaptcha(String account){
        String res="login:"+account;
        return res;
    }
    //重置密码验证码
    public static String getRePasswordCaptcha(String account){
        String res="password:"+account;
        return res;
    }
    public static String getUserTicket(String uuid){
        String ticket="ticket:"+uuid;
        return ticket;
    }
    public static String getShareTicket(String uuid){
        String ticket="share"+uuid;
        return ticket;
    }
}
