package com.ida;

import com.ida.dao.CompanyDao;
import com.ida.entity.Requirement;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ApplicationTests {

    @Autowired
    CompanyDao companyDao;
    Integer id =1;
    @Test
    public void selectAllReq() {
        System.out.println(companyDao.selectAllReq(id));
    }

}
