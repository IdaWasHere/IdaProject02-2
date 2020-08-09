package com.ida.service;

import com.ida.entity.Enroll;
import com.ida.entity.Requirement;

import java.util.Date;
import java.util.List;

public interface CompanyService {

    /**
     * 查找所在企业的所有需求
     */
    List<Requirement> selectAllReq(Integer companyId);

    /**
     * 添加需求
     */
    void addRequirement(Requirement requirement);

    /**
     * 根据ID查找需求
     */
    Requirement selectReqById(Integer id);

    /**
     * 修改需求
     */
    void updateRequirement(Requirement requirement);

    /**
     * 删除需求
     */
    void deleteRequirement(Integer id);

    /**
     * 查看报名情况
     */
    List<Enroll> selectEnroll(Integer id);

    /**
     * 通过报名
     */
    void agreeEnroll(int urid);

    /**
     * 不通过报名
     */
    void refuseEnroll(int urid);
}
