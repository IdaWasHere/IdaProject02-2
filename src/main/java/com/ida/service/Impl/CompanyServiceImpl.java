package com.ida.service.Impl;

import com.ida.dao.CompanyDao;
import com.ida.entity.Enroll;
import com.ida.entity.Requirement;
import com.ida.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyDao companyDao;

    @Override
    public List<Requirement> selectAllReq(Integer companyId) {
        System.out.println(companyDao.selectAllReq(companyId));
        return  companyDao.selectAllReq(companyId);
    }

    @Override
    public void addRequirement(Requirement requirement) {
        companyDao.addRequirement(requirement);
    }

    @Override
    public Requirement selectReqById(Integer id) {
        return companyDao.selectReqById(id);
    }

    @Override
    public void updateRequirement(Requirement requirement){
        companyDao.updateRequirement(requirement);
    }

    @Override
    public void deleteRequirement(Integer id) {
        companyDao.deleteRequirement(id);
    }

    @Override
    public List<Enroll> selectEnroll(Integer id) {
        return companyDao.selectEnroll(id);
    }

    @Override
    public void agreeEnroll(int urid) {
        companyDao.agreeEnroll(urid);
    }

    @Override
    public void refuseEnroll(int urid) {
        companyDao.refuseEnroll(urid);
    }


}
