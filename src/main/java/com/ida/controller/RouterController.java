package com.ida.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RouterController {

    /*根路径*/
    // 记得导入thymeleaf依赖，来识别
    @RequestMapping({"/","/index.html"})
    public String index(){
        return "index";
    }


    @RequestMapping("/level1/updatePassword")
    public String updatePassword(){
        return "views/level1/updatePassword";
    }

    @RequestMapping("/level1/userRequirementList")
    public String userRequirement(){
        return "redirect:/user/toList1";
    }

    @RequestMapping("/level2/selectAllReq")
    public String selectAllReq(){
        return "redirect:/toSelectAllReq";
    }

    @RequestMapping("/level2/selectEnroll")
    public String selectEnroll(){
        return "redirect:/toSelectEnroll";
    }

    @RequestMapping("/level1/companyList")
    public String companyList(){
        return "redirect:/user/toCompanyList";
    }

    @RequestMapping("/level1/myEnrollList")
    public String myEnrollList(){
        return "redirect:/user/myEnrollList";
    }

    @RequestMapping("/level3/identityList")
    public String identityList(){
        return "redirect:/identityList";
    }

    @RequestMapping("/level3/requirementReadList")
    public String requirementReadList(){
        return "redirect:/requirementReadList";
    }

    @RequestMapping("/level3/selectEnrollList")
    public String selectEnrollList(){
        return "redirect:/toSelectEnrollList";
    }

    @RequestMapping("/level3/userList")
    public String userList(){
        return "redirect:/toSelectUserList";
    }
}
