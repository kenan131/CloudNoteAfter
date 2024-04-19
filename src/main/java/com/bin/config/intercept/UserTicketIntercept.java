package com.bin.config.intercept;

import com.bin.model.entity.User;
import com.bin.service.imple.UserService;
import com.bin.util.HostHolder;
import com.bin.util.RedisKeyUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class UserTicketIntercept implements HandlerInterceptor {
    @Autowired
    private HostHolder hostHolder;

   @Autowired
   private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        if(!StringUtils.isBlank(token)){
            User user = userService.getUserByTicket(token);
            if(user!=null){
                hostHolder.setUser(user);
            }
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        hostHolder.clear();
    }
}
