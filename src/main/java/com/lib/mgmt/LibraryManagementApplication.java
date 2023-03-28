package com.lib.mgmt;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LibraryManagementApplication {

	//http://localhost:8081/swagger-ui/index.html
	//https://mkyong.com/spring-boot/spring-rest-validation-example/
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(LibraryManagementApplication.class);
		app.setBannerMode(Banner.Mode.OFF);
		app.run(args);
	}

}
