package com.teneke.shophelper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages="com.teneke.shophelper.repository")
public class ShophelperApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShophelperApplication.class, args);
	}

}

