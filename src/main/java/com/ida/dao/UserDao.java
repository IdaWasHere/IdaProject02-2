package com.ida.dao;
import com.ida.entity.Enroll;
import com.ida.entity.Requirement;
import com.ida.entity.User;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface UserDao {

    /**
     * 注册用户
     * @param user
     */
    void save(User user);

    /**
     * 根据用户名查看用户
     */
    public User findByUsername(String userName);


    /**
     * 根据用户名查找对应密码
     */
    public String findPassword(String username);

    /**
     * 根据用户名修改密码为加密的新密码
     */
    void updateCryptoPassword(String user_name, String crypto_password);

    /**
     * 分页查询总记录数
     * @return
     */
    int findTotalCount();

    /**
     * 分页查询每页记录
     * @param start
     * @param rows
     * @return
     */
    List findByPage(int start, int rows);

    /**
     * 模糊查询
     * @param fuzzy
     * @return
     */
    int findTotalCountByFuzzy(String fuzzy);

    /**
     * 模糊查询
     */
    List findByFuzzy(int start, int rows,String fuzzy);


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
    int queryImg(String username, String picPath, String locPath);

    /**
     * 根据用户名修改头像-
     */
    void updateImg(String username, String pic);

    //根据用户名查找该用户的头像，如果为null.jpg,则在controller层比较，确认为默认头像
    String findPicByUsername(String username);
}

























