package com.ida.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class MyMvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/register.html").setViewName("register");
        registry.addViewController("/login.html").setViewName("login");
        registry.addViewController("/findPassword.html").setViewName("findPassword");
    }

    /**
     * 资源映射路径
     */
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            //在磁盘apple目录下的所有资源会被解析成以下路径
            registry.addResourceHandler("/src/main/resources/static/img/**").addResourceLocations("classpath:/img/");
            WebMvcConfigurer.super.addResourceHandlers(registry);
        }

/*    @Autowired
    private LoginInterceptor loginHandlerInterceptor;
    //注册拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration ir = registry.addInterceptor(loginHandlerInterceptor).addPathPatterns("/**")
                .excludePathPatterns("/login.html", "/user/login","/getVerity","/sendMail","/forgetPassword","/register","/","/findPassword.html",
                                     "/static/**","/user/pic",
                                     "/level1/*","/level2/*","/level3/*"
                                     );
    }*/
}
