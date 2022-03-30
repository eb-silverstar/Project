package com.osc.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(value = {"com.osc.user.mapper"})
@SpringBootApplication
public class UserCRUDApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserCRUDApplication.class, args);
	}

}
