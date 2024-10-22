package com.github.fttroy.testsecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication
public class TestsecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestsecurityApplication.class, args);
	}

}
