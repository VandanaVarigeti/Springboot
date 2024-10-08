package com.org.capstone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
@EnableAsync
public class CapstoneApplication {

	public static void main(String[] args) {

		SpringApplication.run(CapstoneApplication.class, args);
	}

}
