package com.ida.service.Impl;

import com.ida.dao.UserDao;
import com.ida.entity.Enroll;
import com.ida.entity.PageBean;
import com.ida.entity.User;
import com.ida.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private  String sendUsername;

    @Override
    public void register(User user) {
        userDao.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public String findPassword(String username) {
        return userDao.findPassword(username);
    }

    @Override
    public void sendMail(String to, String subjet, String content) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setSubject(subjet);
        mailMessage.setText(content);
        mailMessage.setTo(to);
        mailMessage.setFrom(sendUsername);
        javaMailSender.send(mailMessage);
    }

    @Override
    public void updateCryptoPassword(String user_name, String crypto_password) {
        userDao.updateCryptoPassword(user_name,crypto_password);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //从数据库获取该用户
        User user = null;
        user = userDao.findByUsername(username);
        System.out.println(user);

        //--------------认证账号
        if (user == null){
            throw new UsernameNotFoundException("用户不存在");
        }

        //----------开始授权
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        if (!StringUtils.isEmpty(user.getRoles())) {
            String[] roles = user.getRoles().split(",");
            for (String role : roles) {
                if (!StringUtils.isEmpty(role)) {
                    authorities.add(new SimpleGrantedAuthority(role.trim()));
                }
            }
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),authorities);
    }

    /**
     * 更改头像
     * @param username
     * @param pic
     */
    @Override
    public void updateImg(String username, String pic) {
       userDao.updateImg(username,pic);
    }

    @Override
    public void upload(String username, byte[] file, String filePath, String fileName) {
        //判断上传文件的保存目录是否存在
        File targetFile = new File(filePath);
        if (!targetFile.exists() && !targetFile.isDirectory()){
            System.out.println(filePath+"目录不存在，需要创建");
            //创建目录
            targetFile.mkdirs();
        }

        try {
            FileOutputStream out = null;
            out = new FileOutputStream(fileName);
            out.write(file);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String findPicByUsername(String username) {
        return userDao.findPicByUsername(username);
    }

    @Override
    public PageBean findListByPage(String currentPage, int rows) {


        //1、创建空的pagebean对象
        PageBean pb = new PageBean();
        //2、设置参数
        pb.setCurrentPage(currentPage);
        pb.setRows(rows);

        //3、调用dao查询总记录数
        int totalCount =  userDao.findTotalCount();
        pb.setTotalCount(totalCount);
        //4、调用dao查询list集合
            //计算开始的记录索引

        int _currentPage = Integer.parseInt(currentPage);
        int start = (_currentPage-1)*rows;
        List list = userDao.findByPage(start,rows);
        pb.setList(list);

        //5、计算总页码
        int totalPage = (totalCount % rows) == 0 ? totalCount/rows: (totalCount/rows) +1 ;
        pb.setTotalPage(totalPage);

        return pb;
    }

    @Override
    public PageBean findByFuzzy(String currentPage, int rows,String fuzzy) {
        PageBean pb = new PageBean();
        pb.setCurrentPage(currentPage);
        pb.setRows(rows);

        int totalCount =  userDao.findTotalCountByFuzzy(fuzzy);
        pb.setTotalCount(totalCount);

        int _currentPage = Integer.parseInt(currentPage);
        int start = (_currentPage-1)*rows;
        List list = userDao.findByFuzzy(start,rows,fuzzy);
        pb.setList(list);

        int totalPage = (totalCount % rows) == 0 ? totalCount/rows: (totalCount/rows) +1 ;
        pb.setTotalPage(totalPage);

        return pb;
    }

    @Override
    public void enroll2(Enroll enroll) {
        userDao.enroll2(enroll);
    }

    @Override
    public List myEnrollList(int uid) {
        return userDao.myEnrollList(uid);
    }

    @Override
    public List companyList() {
        return userDao.companyList();
    }

    @Override
    public void identity(int uid, int cid) {
        userDao.identity(uid,cid);
    }

}
















