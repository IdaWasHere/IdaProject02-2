package com.ida.dao;

import com.ida.entity.Identity;
import com.ida.entity.Requirement;

import java.util.List;

public interface AdminDao {

    /**
     * 查看认证列表
     */
    List<Identity> identity();

    /**
     * 管理员通过用户认证-
     */
    void agreeIdentity(int uid,int cid);

    /**
     * 管理员查看 企业用户提交的需求 列表
     */
    List<Requirement> requirementReadList();

    /**
     * 管理员审核 需求
     */
    void readReq(Integer id);

    void readReq2(Integer id);

    /**
     * 查看报名表
     */
    List selectEnrollList();

    /**
     * 查询用户列表
     */
    List toSelectUserList();

    /**
     * -列入黑名单
     */
    void black(int id);
}
