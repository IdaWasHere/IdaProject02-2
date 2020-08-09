package com.ida.util;

import java.util.Random;

public class FindPwdUtil {

    /**
     * 设置验证码6位随机数
     */
    public static String getSixRandom(){
        Random random = new Random();
        String result = "";
        for(int i = 0;i < 6; i++){
            result += random.nextInt(10);
        }
        return result;
    }

}
