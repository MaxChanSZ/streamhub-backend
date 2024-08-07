package com.fdmgroup.backend_streamhub;

import com.fdmgroup.backend_streamhub.security.RSAKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@SpringBootApplication
@EnableConfigurationProperties(RSAKeyProperties.class)
public class BackendStreamhubApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendStreamhubApplication.class, args);
	}

}
