package com.msstats;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsstatsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsstatsApplication.class, args);
	}

}
