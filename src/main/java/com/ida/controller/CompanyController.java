package com.ida.controller;

import com.ida.dao.CompanyDao;
import com.ida.entity.Enroll;
import com.ida.entity.Requirement;
import com.ida.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.UUID;


@Controller                    //注意！！！！！！！
public class CompanyController {

    @Autowired
    private CompanyService companyService;
    @Autowired
    CompanyDao companyDao;

    //企业用户查找所在企业发布的所有需求
    @GetMapping("/toSelectAllReq")
    public String selectAllReq(Model model, HttpSession session){
        Integer companyId = (Integer) session.getAttribute("identityCompany");

        Collection<Requirement> requirements = companyService.selectAllReq(companyId);
        model.addAttribute("requirements",requirements);
        return "views/level2/requirementList";
    }

    @GetMapping("/toAdd")
    public String toAdd(){
        return "views/level2/addRequirement";
    }

    @PostMapping("/addRequirement")
    public String addRequirement(Requirement requirement, HttpSession session, @RequestParam("file")MultipartFile file, BindingResult bindingResult) throws IOException {

        //上传文件
        //1、获取原始名字
        String fileName = file.getOriginalFilename();
        System.out.println(fileName);
        //2、获取后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        //3、文件保存路径
        String filePath = "c:/upload/";
        //4、文件重命名，防止重复
        fileName = filePath + UUID.randomUUID() + fileName;
        System.out.println(fileName);
        //5、文件对象
        File dest = new File(fileName);
        //6、判断路径是否存在，不存在则创建
        if (!dest.getParentFile().exists()){
            dest.getParentFile().mkdirs();
        }
        file.transferTo(dest);

        //取出登录时企业用户的identityCompany
        Integer identityCompany = (Integer) session.getAttribute("identityCompany");
        requirement.setCompanyid(identityCompany);
        requirement.setFilePath(fileName);
        //添加操作
        companyService.addRequirement(requirement);

        return "redirect:/toSelectAllReq";
    }

    @GetMapping("/toUpdate/{id}")
    public String toUpdate(@PathVariable("id") Integer id, Model model){
        //查出原来的数据
        Requirement requirement = companyService.selectReqById(id);
        model.addAttribute("requirementById",requirement);
        return "views/level2/updateRequirement";
    }

    @PostMapping("/updateRequirement")
    public String updateRequirement(Requirement requirement){
        companyService.updateRequirement(requirement);
        return "redirect:/toSelectAllReq";
    }

    @GetMapping("/deleteRequirement/{id}")
    public String deleteRequirement(@PathVariable("id") Integer id){
        companyService.deleteRequirement(id);
        return "redirect:/toSelectAllReq";
    }

    @GetMapping("/toSelectEnroll")
    public String selectEnroll(HttpSession session, Model model){
        Integer identityCom = (Integer) session.getAttribute("identityCompany");

        Collection<Enroll> enrolls = companyService.selectEnroll(identityCom);
        System.out.println("------"+((List<Enroll>) enrolls).get(0).getUrid());

        model.addAttribute("enrolls",enrolls);
        return "views/level2/enrollList";
    }

    /**
     * 通过报名
     */
    @GetMapping("/agreeEnroll/{urid}")
    public String agreeEnroll(@PathVariable("urid") int urid){
        companyService.refuseEnroll(urid);
        return "redirect:/toSelectEnroll";
    }

    /**
     * 不通过报名
     */
    @GetMapping("/refuseEnroll/{urid}")
    public String refuseEnroll(@PathVariable("urid") int urid){
        companyService.agreeEnroll(urid);
        return "redirect:/toSelectEnroll";
    }
}
















