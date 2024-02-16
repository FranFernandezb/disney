package com.alkemy.disney;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class DisneyApplication {

	public static void main(String[] args) {
		SpringApplication.run(DisneyApplication.class, args);
	}

	//First stage:
	//TODO: Use tokens to access to controllers, and validate them
	//TODO: Try-out the complete product

	//Second stage:
	//TODO: Add tests to the project.
	//TODO: Dockerize it (if possible)


}
