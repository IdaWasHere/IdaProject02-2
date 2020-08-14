package com.ida.controller;

import com.ida.entity.Enroll;
import com.ida.entity.PageBean;
import com.ida.entity.User;
import com.ida.service.UserService;
import com.ida.util.MD5Utils;
import com.ida.util.RandomValidateCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    private AuthenticationManager myAuthenticationManager;

    RandomValidateCodeUtil randomValidateCodeUtil = new RandomValidateCodeUtil();

    //进行注册   与register.html的action对应
    @PostMapping("/register")
    public String register(User user, @RequestParam("username") String username, Model model,HttpSession session) {

        User findUser = userService.findByUsername(username);

        if (findUser == null) {
            user.setPassword(MD5Utils.encode(user.getPassword()));
            userService.register(user);
            //跳转到登录页面
            return "redirect:/user/toLogin";
        } else {
            model.addAttribute("registermsg", "该用户名已被注册！");
            return "register";
        }

    }

    @GetMapping("toBack")
    public String toBack(Model model,HttpServletRequest request){
        String username = (String) request.getSession().getAttribute("username");
        model.addAttribute("username",username);
        return "index";
    }

    @GetMapping("/toLogin")
    public String toLogin(Model model, HttpServletRequest request,HttpServletResponse response){
        Object[] objects =  randomValidateCodeUtil.getRandcode(request,response);
        model.addAttribute("randomCode", objects[0]);

        System.out.println("getverity时调用util的objects[0],顺便存到model中的验证码"+(String) request.getSession().getAttribute("randomCode"));
        request.getSession().setAttribute("random_img",objects[1]);
        return "login";
    }

    @GetMapping(value = "/login")
    public String login(
            //@RequestParam为了保险接收参数 Model model为了回显数据
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            Model model,
            HttpServletRequest request, HttpServletResponse response) {

        //为了需求列表与企业用户的企业对应
        User findUser = userService.findByUsername(username);
        Integer identityCompany= findUser.getIdentityCompany();
        request.getSession().setAttribute("identityCompany",identityCompany);
        //为了报名时能得到uid
        Integer uid = findUser.getId();
        request.getSession().setAttribute("uid",uid);
        //把username存进session，以便于头像上传时把图片路径插入对应用户的数据库表
        request.getSession().setAttribute("username",username);
        //便于在index页面得到locPath
        model.addAttribute("user",findUser);

        String code = request.getParameter("code");
        //获取model中的验证码
       String randomCode = request.getParameter("randomCode");
        System.out.println("login_model******"+randomCode);

      if (StringUtils.isEmpty(code) || !randomCode.equals(code)){
          model.addAttribute("msg", "验证码错误！");
          return "redirect:/user/toLogin";
      }

      //忘记密码，七天免登
        if (username == null && password == null){
            Cookie[] cookies = request.getCookies();
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("cookie_username")){
                    username = cookie.getValue();
                    break;
                }
            }
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("cookie_password")){
                    password = cookie.getValue();
                    break;
                }
            }
        }



        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username,password);

        //用spring Security拦截登录请求，进行认证和授权
        Authentication authenticate = myAuthenticationManager.authenticate(usernamePasswordAuthenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        HttpSession session = request.getSession();
        //关系到验证后的登录
        session.setAttribute("SPRING_SECURITY_CONTEXT",SecurityContextHolder.getContext());
        //修改头像需要用到username
        session.setAttribute("username",username);

        //默认头像
        String filename="C:\\Users\\86136\\02trading\\src\\main\\resources\\static\\img\\"+ username +".jpg";
        String pic = userService.findPicByUsername(username);
        if (!pic.equals(filename)){
            model.addAttribute("username",null);
        }else {
            model.addAttribute("username",username);
        }


       //记住密码
        String remember = request.getParameter("remember");
        if (remember!=null) {
            //将登录用户信息保存到session中
            session.setAttribute("findUser", findUser);
            //保存cookie,实现自动登录
            Cookie cookie_username = new Cookie("cookie_username", username);
            Cookie cookie_password= new Cookie("cookie_password", password);
            //设置cookie的持久化时间，7天
            cookie_username.setMaxAge(7 * 24 * 60 * 60);
            cookie_password.setMaxAge(7 * 24 * 60 * 60);
            // 设置为当前项目下都携带这个cookie
            cookie_username.setPath("/");
            cookie_password.setPath("/");
            // 向客户端发送cookie
            response.addCookie(cookie_username);
            response.addCookie(cookie_password);
        }
        return "index";
    }


    //退出登录
    @GetMapping("/toLogout")
    public String logout(HttpSession session,HttpServletRequest request ,HttpServletResponse response){
        //保存cookie,实现自动登录
        Cookie cookie_username = new Cookie("cookie_username","");
        Cookie cookie_password = new Cookie("cookie_password","");
        //设置cookie的持久化时间，0
        cookie_username.setMaxAge(0);
        cookie_password.setMaxAge(0);
        //设置当前项目下都携带这个cookie
        cookie_username.setPath("/");
        cookie_password.setPath("/");
        // 向客户端发送cookie
        response.addCookie(cookie_username);
        response.addCookie(cookie_password);
        return "redirect:/user/toLogin";
    }

    @PostMapping("/forgetPassword")
    public String forgetPassword(HttpServletRequest request,Model model){
        String veritycode = request.getParameter("veritycode");
        String verity_code = (String) request.getSession().getAttribute("mailCode");
        if(!StringUtils.isEmpty(veritycode) && veritycode.equals(verity_code)){
            //验证成功 ， 跳转到首页
            return "redirect:/updatePassword";
        }else{
            model.addAttribute("verityfail","验证码错误");
            return "findPassword";
        }

    }

    @PostMapping("/updatePassword")
    public String updatePassword(@RequestParam("username") String username ,
                                 @RequestParam("oldpassword") String oldpassword,
                                 @RequestParam("newpassword") String newpassword,
                                 Model model){

        String find_oldpassword = userService.findPassword(username);
        String md5_oldpassword = MD5Utils.encode(oldpassword);
        //确保用户名对应的旧密码是正确的
        if(!StringUtils.isEmpty(username) && find_oldpassword.equals(md5_oldpassword)){
            //接受新密码，加密更改存入数据库
            String md5_passworrd = MD5Utils.encode(newpassword);
            userService.updateCryptoPassword(username,md5_passworrd);
            return "redirect:/user/toBack";

        }else {
            model.addAttribute("oldpwdwrong","旧密码 错误");
            return "updatePassword";
        }
    }


    @GetMapping("/toList1")
    public String fuzzySearch(/*@RequestParam("currentPage")String currentPage*/HttpServletRequest request, Model model){

        String currentPage = request.getParameter("currentPage");
        String fuzzy = request.getParameter("fuzzy");

        if (currentPage == null || "".equals(currentPage)){
            currentPage = "1";
        }
        if(fuzzy == null){
            fuzzy = "%";
        }

        int rows = 3;
        PageBean pb =  userService.findByFuzzy(currentPage,rows,fuzzy);
        System.out.println(pb.getTotalPage());
        model.addAttribute("pb",pb);
        System.out.println(pb);
        return "views/level1/userRequirementList";
    }

    /**
     * 从list表获取 reqid ,转入enroll.html表的隐藏域中
     */
    @GetMapping("/enroll1/{id}")
    public String enroll(Model model,@PathVariable("id") Integer id ){
        int reqid = id;
        System.out.println(id);
        model.addAttribute("reqid",reqid);
        return "views/level1/enroll";
    }

    /**
     * 报名
     */
    @PostMapping("/enroll2")
    public String enroll2(HttpServletRequest request){
        //得到用户自身的id
        int uid = (int) request.getSession().getAttribute("uid");

        //得到 隐藏域中的reqid 和 填写报名报表的信息
        int reqid = Integer.parseInt(request.getParameter("reqid"));
        String realname = request.getParameter("realname");
        int phone = Integer.parseInt(request.getParameter("phone"));
        int age = Integer.parseInt(request.getParameter("age"));
        String address = request.getParameter("address");

        Enroll enroll = new Enroll();
        enroll.setUid(uid);
        enroll.setReqid(reqid);
        enroll.setRealname(realname);
        enroll.setPhone(phone);
        enroll.setAge(age);
        enroll.setAddress(address);

        userService.enroll2(enroll);
        return "redirect:/user/toList1";
    }

    @GetMapping("/myEnrollList")
    public String myEnrollList(HttpServletRequest request,Model model){
        //得到用户自身的id
        int uid = (int) request.getSession().getAttribute("uid");
        List myEnrolls = userService.myEnrollList(uid);
        model.addAttribute("myEnrolls",myEnrolls);
        return "views/level1/myEnrollList";
    }

    @GetMapping("/toCompanyList")
    public String toCompanyList(Model model){
        List companys = userService.companyList();
        model.addAttribute("companys",companys);
        return "views/level1/companyList";
    }

    @GetMapping("/identity/{comid}")
    public String identity(@PathVariable("comid") int comid, HttpServletRequest request){
        //得到用户自身的id
        int uid = (int) request.getSession().getAttribute("uid");
        userService.identity(uid,comid);
        return "redirect:/user/toCompanyList";
    }

    @PostMapping("/pic")
    public String pic(@RequestParam("file") MultipartFile file,Model model,HttpSession session) throws IOException {
        String username = (String) session.getAttribute("username");
        if (!file.isEmpty()){

                BufferedOutputStream out = new BufferedOutputStream(
                        //保存图片到目录下
                        new FileOutputStream(new File("C:\\Users\\86136\\02trading\\src\\main\\resources\\static\\img\\"+ username +".jpg")));
                out.write(file.getBytes());
                out.flush();
                out.close();
                String filename="C:\\Users\\86136\\02trading\\src\\main\\resources\\static\\img\\"+ username +".jpg";
                userService.updateImg(username,filename);

            model.addAttribute("filename",filename);
            model.addAttribute("username",username);
            System.out.println(filename);
            System.out.println(username);
            return "index";
        }else {
            return "上传失败，空文件";
        }
    }

    //为了进入user/login的控制层，免登权限实现，即使游客模式也能进行
    @RequestMapping("/")
    public String toLoginIndex(){
        return "redirect:/user/login";
    }

}