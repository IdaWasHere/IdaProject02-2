package com.ida.service.Impl;

import com.ida.dao.AdminDao;
import com.ida.entity.Identity;
import com.ida.entity.Requirement;
import com.ida.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private  String sendUsername;
    @Autowired
    AdminDao adminDao;

    @Override
    public List<Identity> identity() {
        List<Identity> list = adminDao.identity();
        return adminDao.identity();
    }

    @Override
    public void agreeIdentity(int uid, int cid) {
        adminDao.agreeIdentity(uid,cid);
    }

    @Override
    public void sendMessage(String to, String subjet, String content) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setSubject(subjet);
        mailMessage.setText(content);
        mailMessage.setTo(to);
        mailMessage.setFrom(sendUsername);
        javaMailSender.send(mailMessage);
    }

    @Override
    public List<Requirement> requirementReadList() {
        return adminDao.requirementReadList();
    }

    @Override
    public void readReq(Integer id) {
        adminDao.readReq(id);
    }

    @Override
    public void readReq2(Integer id) {
        adminDao.readReq2(id);
    }

    @Override
    public List selectEnrollList() {
        return adminDao.selectEnrollList();
    }

    @Override
    public List toSelectUserList() {
        return adminDao.toSelectUserList();
    }

    @Override
    public void black(int id) {
        adminDao.black(id);
    }
}
