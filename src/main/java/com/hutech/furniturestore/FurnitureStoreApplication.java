package com.hutech.furniturestore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.hutech.furniturestore")
public class FurnitureStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(FurnitureStoreApplication.class, args);
	}

}
