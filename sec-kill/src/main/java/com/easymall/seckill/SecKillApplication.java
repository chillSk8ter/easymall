package com.easymall.seckill;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableRabbit
public class SecKillApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecKillApplication.class, args);
    }

}
