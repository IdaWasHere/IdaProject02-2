package com.ida.controller;

import com.ida.entity.Identity;
import com.ida.entity.Requirement;
import com.ida.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

@Controller
public class AdminController {

    @Autowired
    AdminService adminService;

    //查看认证列表
    @RequestMapping("/identityList")
    public String identityList(Model model){
        List<Identity> identitys = adminService.identity();
        model.addAttribute("identitys",identitys);
        return "views/level3/identityList";
    }

    //管理员通过用户认证
    @GetMapping("/agreeIdentity")
    public String agreeIdentity(@RequestParam("uid") int uid, @RequestParam("cid") int cid, @RequestParam("email") String email){
        adminService.agreeIdentity(uid,cid);

        //发信息
       adminService.sendMessage(email,"认证结果","认证成功，您已是企业用户");

        return "redirect:/identityList";
    }

    //管理员不通过用户认证
    @GetMapping("/refuseIdentity/{email}")
    public String refuseIdentity( @PathVariable("email") String email){

        //发信息
        adminService.sendMessage(email,"认证结果","认证失败");

        return "redirect:/identityList";
    }

    //管理员查看 企业用户提交的需求 列表
    @GetMapping("/requirementReadList")
    public String requirementReadList(Model model){
        List<Requirement> requirementReads = adminService.requirementReadList();
        model.addAttribute("requirementReads",requirementReads);
        return "views/level3/requirementReadList";
    }

    //企业用户的需求 通过审核
    @GetMapping("/agreeReq")
    public String agreeReq(@RequestParam("id") Integer id,@RequestParam("companyemail")String companyemail){
        adminService.readReq(id);
        adminService.sendMessage(companyemail,"需求审核结果","审核通过");
        return "redirect:/requirementReadList";
    }

    @GetMapping("/refuseReq")
    public String refuseReq(@RequestParam("id") Integer id,@RequestParam("companyemail")String companyemail) {
        adminService.readReq2(id);
        adminService.sendMessage(companyemail,"需求审核结果","审核未通过");
        return "redirect:/requirementReadList";
    }

    //查询报名列表
    @GetMapping("/toSelectEnrollList")
    public String selectEnrollList(Model model){
        List enrolls = adminService.selectEnrollList();
        model.addAttribute("enrolls",enrolls);
        return "views/level3/selectEnrollList";
    }

    //查询用户列表
    @GetMapping("/toSelectUserList")
    public String toSelectUserList(Model model){
        List users = adminService.toSelectUserList();
        model.addAttribute("users",users);
        return "views/level3/userList";
    }

    //列入黑名单
    @GetMapping("/black/{id}")
    public String black(@PathVariable("id") int id){
        adminService.black(id);
        return "redirect:/toSelectUserList";
    }

    //下载附件
    @GetMapping("/download")
    public void download(@RequestParam("filePath") String filePath, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String fileName = filePath;
        //    要下载的文件
        File file = new File(fileName);
        //如果文件不存在
        if (!file.exists()){
            request.setAttribute("message","您要下载的资源已删除");
        }
        //处理文件名
        String realname = fileName.substring(fileName.indexOf("_")+1);
        //设置响应头，控制浏览器下载该文件
        response.setHeader("content-disposition","attachment;filename=" + URLEncoder.encode(realname,"UTF-8"));
        //读取要下载的文件，保存文件输入流
        FileInputStream in = new FileInputStream(fileName);
        //创建输出流
        OutputStream out = response.getOutputStream();
        //创建缓冲区
        byte buffer[] = new  byte[1024];
        int len = 0;
        //循环将输入流中的内容读取到缓冲区
        while((len=in.read(buffer))>0){
            //输出缓冲区的内容到浏览器，实现文件下载
            out.write(buffer,0,len);
        }
        //关闭文件输入流
        in.close();
        //关闭输出流
        out.close();
    }
}
