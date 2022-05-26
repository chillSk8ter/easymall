package com;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = "com.moudle.pms")
@EnableDiscoveryClient
@MapperScan("com.moudle.pms.mapper")
@EnableCaching
@EnableFeignClients(basePackages = {"com.moudle.pms.feign"})
public class EasymallProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(EasymallProductApplication.class, args);
    }

}


    