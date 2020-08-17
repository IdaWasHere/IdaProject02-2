package com.ida.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class MyMvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/register.html").setViewName("register");
        registry.addViewController("/index.html").setViewName("index");
        registry.addViewController("/login.html").setViewName("login");
        registry.addViewController("/findPassword.html").setViewName("findPassword");
        registry.addViewController("/updatePassword.html").setViewName("updatePassword");
    }

    /**
     * 资源映射路径
     */
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            //在磁盘apple目录下的所有资源会被解析成以下路径
             registry.addResourceHandler("/img/**").addResourceLocations("file:/C:/Users/86136/02trading/src/main/resources/static/img/");
        }

  /*  @Autowired
    private LoginInterceptor loginHandlerInterceptor;
    //注册拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration ir = registry.addInterceptor(loginHandlerInterceptor).addPathPatterns("/**")
                .excludePathPatterns("/login.html", "/user/toLogin","/user/login","/getVerity","/sendMail","/user/forgetPassword","/findPassword.html",
                        "/user/register","user/toBack","/user/toLogout","user/updatePassword","/register.html",
                        "/static/**","/user/pic"
                );
    }*/
}
