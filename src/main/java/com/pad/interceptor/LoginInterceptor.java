package com.pad.interceptor;

import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        Object user = session.getAttribute("user");
        if (ObjectUtils.isEmpty(user)){
            response.sendRedirect(request.getContextPath()+"/signIn");
            return false;
        }else {
            System.out.println(user);
            return true;
        }
    }
}
