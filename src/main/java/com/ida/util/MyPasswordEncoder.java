package com.ida.util;

import org.springframework.security.crypto.password.PasswordEncoder;

//自定义验证密码类 密码比较器
public class MyPasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence charSequence) {
        return MD5Utils.encode((String) charSequence);
    }

    @Override
    public boolean matches(CharSequence charSequence, String encodedPassword) {
        //user Details Service验证
        return encodedPassword.equals(MD5Utils.encode((String) charSequence));
    }
}
