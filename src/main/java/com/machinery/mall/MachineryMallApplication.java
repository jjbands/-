package com.machinery.mall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.machinery.mall.mapper")
public class MachineryMallApplication {
    public static void main(String[] args) {
        SpringApplication.run(MachineryMallApplication.class, args);
    }
}