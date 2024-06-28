package com.hutech.furniturestore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.cloudinary.*;
import com.cloudinary.utils.ObjectUtils;
import io.github.cdimascio.dotenv.Dotenv;

import java.util.Map;

@SpringBootApplication
public class FurnitureStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(FurnitureStoreApplication.class, args);
	}

}
