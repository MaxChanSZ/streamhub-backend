package com.fdmgroup.backend_streamhub;

import com.fdmgroup.backend_streamhub.security.RSAKeyProperties;
import externalServices.video_processing_service.VideoProcessingService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@SpringBootApplication
@EnableConfigurationProperties(RSAKeyProperties.class)
public class BackendStreamhubApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendStreamhubApplication.class, args);

		System.out.println("Streamhub backend has started running.");
		VideoProcessingService service = new VideoProcessingService();
		service.mp4ToHls("steamboatwillie_001.webm");
	}

}
