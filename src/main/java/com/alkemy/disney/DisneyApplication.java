package com.alkemy.disney;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DisneyApplication {

	public static void main(String[] args) {
		SpringApplication.run(DisneyApplication.class, args);
	}

	//First stage:
	//TODO: Manage exceptions at Controller level.
	//TODO: Delete hardcoded messages for constants with String.format fields.
	//TODO: Use tokens to access to controllers, and validate them
	//TODO: Add logs to project in all levels.
	//TODO: Refactor things, use mappers, ternary operators, streams and lambdas, methods by reference, etc.
	//TODO: Try-out the complete product

	//Second stage:
	//TODO: Add tests to the project.
	//TODO: Dockerize it (if possible)


}
