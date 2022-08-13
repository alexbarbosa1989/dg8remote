package com.redhat.dg8remote;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class RemoteQueryApplication {

	public static void main(String[] args) {
		SpringApplication.run(RemoteQueryApplication.class, args);
	}

}
