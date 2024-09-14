package com.wipro.brokerage_api_cloud_gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@EnableDiscoveryClient
public class BrokerageApiCloudGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(BrokerageApiCloudGatewayApplication.class, args);
	}

}
