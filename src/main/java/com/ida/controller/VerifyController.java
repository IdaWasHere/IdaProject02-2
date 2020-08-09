package com.ida.controller;

import com.ida.service.UserService;
import com.ida.util.FindPwdUtil;
import com.ida.util.RandomValidateCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

@Controller
@ResponseBody
public class VerifyController {

    /**
     * 生成验证码，将验证码图片传给前台界面
     */
    @RequestMapping(value = "/getVerity",method = RequestMethod.GET)
    public void getVerity(HttpServletRequest request, HttpServletResponse response) throws IOException {

        RandomValidateCodeUtil randomValidateCodeUtil = new RandomValidateCodeUtil();
        Object[] objects =  randomValidateCodeUtil.getRandcode(request,response);

        //1、验证码 字符串 给 usercontroller去校对
        request.getSession().setAttribute("randomCode",objects[0]);

        //2、将图片输出给浏览器的html
        BufferedImage image = (BufferedImage) objects[1];
        //设置相应类型，告诉浏览器输出的内容为图片
        response.setContentType("image/png");
        //设置响应头消息，告诉浏览器不要缓存此内容
        response.setHeader("Pragma","No-cache");
        response.setHeader("Cache-Control","no-cache");
        response.setHeader("Set-cookie","name=value;HttpOnly");
        response.setDateHeader("Expire",0);

        OutputStream outputStream = response.getOutputStream();
        ImageIO.write(image,"png",outputStream);
        }

    /**
     * 邮件验证码
     */
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/sendMail",method = RequestMethod.GET)
    public void sendMail(String toMail,HttpServletRequest request){

        System.out.println(toMail);
        String mailCode = FindPwdUtil.getSixRandom();

        //存放在session中，以便校验验证码
        request.getSession().setAttribute("mailCode",mailCode);

        //发邮件
        userService.sendMail(toMail,"邮件验证码",mailCode);
    }

}
















