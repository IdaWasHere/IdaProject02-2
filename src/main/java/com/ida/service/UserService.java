package com.ida.service;

import com.ida.entity.Enroll;
import com.ida.entity.PageBean;
import com.ida.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService extends UserDetailsService {

    /**
     * 添加/修改用户信息
     */
     void register(User user);

    /**
     * 根据用户名找用户
     */
    User findByUsername(String username);

    /**
     * 根据用户名查找对应密码
     */
    public String findPassword(String username);

    /**
     * 发送邮件
     */
    public void sendMail(String to, String subjet, String content);

    /**
     * 根据用户名 修改为已加密的新密码
     */
    void updateCryptoPassword(String user_name,String crypto_password);


    /**
     * 分页查询需求
     * @param currentPage
     * @param rows
     * @return
     */
    PageBean findListByPage(String currentPage, int rows);

    /**
     * 模糊查询
     */
    PageBean findByFuzzy(String currentPage, int rows, String fuzzy);


    /**
     * 往数据库enroll表填写入  uid reqid 和个人信息
     */
    void enroll2(Enroll enroll);

    /**
     *  查看自己的报名列表
     */
    List myEnrollList(int uid);

    /**
     * 企业列表查看
     */
    List companyList();

    /**
     * 提交认证请求
     */
    void identity(int uid, int cid);

    /**
     * 根据用户名修改头像-
     */
    void updateImg(String username, String pic);

    /**
     * 上传文件
     */
    void upload(String username, byte[] file, String filePath, String fileName) throws IOException;

}










