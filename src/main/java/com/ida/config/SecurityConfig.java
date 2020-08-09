package com.ida.config;
import com.ida.service.UserService;
import com.ida.util.MyPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;


//extends @Enablexxx 两个都有，这个类就已经被spring托管了
@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true) //开启方法权限控制
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Bean
    PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    //授权
    //设计模式：链式编程
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //首页所有人能访问，功能页只有对应权限的人才能看到   (横切
        http.authorizeRequests()
                .antMatchers("/","/login.html","/register.html").permitAll()
                .antMatchers("/level1/**").hasAnyRole("USER","COMPANY")
                .antMatchers("/level2/**").hasRole("COMPANY")
                .antMatchers("/level3/**").hasRole("ADMIN");

        //没有权限默认会到登录页,需要开启登录页面  login
        //1、指定登录inout标签名称;
        http.formLogin().usernameParameter("username").passwordParameter("password")
          //2、制定定制的登录请求  (如果是另一方法，则（"userLogin
                 .loginPage("/login.html").failureUrl("/");
        //防止网站攻击
        http.csrf().disable();
        //注销, 跳到首页
        http.logout().logoutSuccessUrl("/");

    }

    //认证
    //在spring security 5.0+新增了很多加密方式
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(new MyPasswordEncoder());
    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
