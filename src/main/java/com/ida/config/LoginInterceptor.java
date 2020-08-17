/*
package com.ida.config;

import com.ida.dao.UserDao;
import com.ida.entity.User;
import com.ida.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private UserDao userDao;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

            //获得cookie
            Cookie[] cookies = request.getCookies();
            //没有cookie信息，则重定向到登录界面
            if(null == cookies){
                response.sendRedirect(request.getContextPath()+"/user/login");
                return false;
            }
            //定义cookie_username,用户的一些登录信息，例如：用户名、密码
            String cookie_username = null;
            String cookie_password = null;
            //获取cookie里面的一些用户信息
            for(Cookie cookie1 : cookies){
                if ("cookie_username".equals(cookie1.getName())){
                    cookie_username = cookie1.getValue();
                    break;
                }
            }
            for(Cookie cookie2 : cookies){
                if ("cookie_password".equals(cookie2.getName())){
                    cookie_password = cookie2.getValue();
                    break;
                }
            }
            //如果cookie里面没有包含用户的一些登录信息，则重定向到登录界面
            if (StringUtils.isEmpty(cookie_username) || StringUtils.isEmpty(cookie_password)){
                response.sendRedirect(request.getContextPath()+"/user/login");
                return false;
            }

            // 已经登录
            User dbUser = userDao.findByUsername(cookie_username);
            if (null == dbUser) {
                return false;
            }
            //验证密码是否正确
            String newPassword = MD5Utils.encode(cookie_password);
            if (!newPassword.equals(dbUser.getPassword())){
                return false;
            }


            if (newPassword.equals(dbUser.getPassword())) {
                request.getSession().setAttribute("msg", "登录成功");
            }
            return true;
    }
}
*/
