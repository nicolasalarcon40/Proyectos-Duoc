package com.colegio.msasignaturas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MsAsignaturasApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsAsignaturasApplication.class, args);
	}

}
