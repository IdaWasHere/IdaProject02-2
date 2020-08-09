package com.ida.util;

import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * 加密工具类
 */
public class CryptographyUtil {
    public final static String SALT ="code";
    /**
     * md5加密
     */
    public static String md5(String password,String salt){
        return new Md5Hash(password,salt).toString();
    }

    public static void main(String[] args){


        String password = "123";
        System.out.println(CryptographyUtil.md5(password,SALT));
    }

}
