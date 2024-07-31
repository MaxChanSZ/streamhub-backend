package com.fdmgroup.backend_streamhub;

import externalServices.video_processing_service.VideoProcessingService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendStreamhubApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendStreamhubApplication.class, args);
		System.out.println("testing running hello world");
		VideoProcessingService service = new VideoProcessingService();
		service.mp4ToHls("testVideo.MOV");
	}

}
