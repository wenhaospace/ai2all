package com.wendell.ai2all;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Ai2allApplication {

	private static final org.slf4j.Logger logger =
			org.slf4j.LoggerFactory.getLogger(Ai2allApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(Ai2allApplication.class, args);
	}

}
