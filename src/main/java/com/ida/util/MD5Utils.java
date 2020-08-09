package com.ida.util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {
    private static final int time = 5;
    private static final String SALT = "code";

    public static String encode(String password) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("MD5 algorithm not available.  Fatal (should be in the JDK).");
        }

        try {
            for (int i = 0; i < time; i++) {
                byte[] bytes = digest.digest((password + SALT).getBytes("utf-8"));
                password = String.format("%032x", new BigInteger(1, bytes));
            }
            return password;
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException("utf-8 encoding not available.  Fatal (should be in the JDK).");
        }
    }

    public static  void main(String[] args){
        System.out.println(MD5Utils.encode("123456"));
    }
}
