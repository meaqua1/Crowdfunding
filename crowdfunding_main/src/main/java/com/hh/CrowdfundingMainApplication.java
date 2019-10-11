package com.hh;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@MapperScan("com.hh.crowdfunding.*.dao")
@ServletComponentScan
@SpringBootApplication
public class CrowdfundingMainApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrowdfundingMainApplication.class, args);
    }


}