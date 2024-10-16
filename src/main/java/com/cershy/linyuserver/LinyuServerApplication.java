package com.cershy.linyuserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@MapperScan("com.cershy.linyuserver.mapper")
@SpringBootApplication
@EnableScheduling
public class LinyuServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(LinyuServerApplication.class, args);
    }

}
