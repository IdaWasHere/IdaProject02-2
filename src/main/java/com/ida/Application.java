package com.ida;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableAutoConfiguration(exclude={JpaRepositoriesAutoConfiguration.class})
@MapperScan("com.ida.dao")
/*@ComponentScan({"com.ida.service","com.ida.controller"})*/
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
