package com.bin.config.intercept;

import com.bin.model.Enum.ResStatus;
import com.bin.util.HostHolder;
import com.bin.util.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author: bin.jiang
 * @date: 2023/3/6 14:30
 **/
@Component
public class LoginIntercept implements HandlerInterceptor {

    @Autowired
    HostHolder hostHolder;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(hostHolder.getUser()==null&&!"OPTIONS".equals(request.getMethod())){
            response.setStatus(401);
            response.setHeader("Access-Control-Allow-Origin", "http://localhost:8080");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
            response.setContentType("application/json;charset=UTF-8");
            try(PrintWriter writer = response.getWriter();){
                String jsonString = Md5Util.getJSONString(ResStatus.ERROR.getType(), "未登录，请重新登录。");
                writer.write(jsonString);
            }catch (Exception e){
                e.printStackTrace();
            }
            return false;
        }
        return true;
    }
}
