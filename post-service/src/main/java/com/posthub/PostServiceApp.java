package com.posthub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class PostServiceApp {
    public static void main(String[] args) {
        SpringApplication.run(PostServiceApp.class, args);
    }
}