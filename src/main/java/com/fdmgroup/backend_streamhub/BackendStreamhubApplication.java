package com.fdmgroup.backend_streamhub;

import com.fdmgroup.backend_streamhub.security.RSAKeyProperties;
import externalServices.video_processing_service.VideoProcessingService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableAsync
@EnableConfigurationProperties(RSAKeyProperties.class)
@ComponentScan(basePackages = {"com.fdmgroup.backend_streamhub", "externalServices"})
@EnableJpaRepositories(basePackages = {"com.fdmgroup.backend_streamhub", "externalServices"})
public class BackendStreamhubApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendStreamhubApplication.class, args);

		System.out.println("Streamhub backend has started running.");
		VideoProcessingService service = new VideoProcessingService();
		service.mp4ToHls("steamboatwillie_001.webm");
	}
}

