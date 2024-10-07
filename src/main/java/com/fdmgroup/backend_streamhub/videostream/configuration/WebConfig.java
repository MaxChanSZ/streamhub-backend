package com.fdmgroup.backend_streamhub.videostream.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
    private final String VIDEO_DIRECTORY_PATH = "file:src/main/resources/encoded/";
    private final String THUMBNAIL_DIRECTORY_PATH = "file:src/main/resources/thumbnails/";
    private final String POLL_OPTION_IMAGE_DIRECTORY_PATH = "file:src/main/resources/pollOptionImages/";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/encoded/**")
                .addResourceLocations(VIDEO_DIRECTORY_PATH);
        registry.addResourceHandler("/thumbnails/**")
                .addResourceLocations(THUMBNAIL_DIRECTORY_PATH);
        registry.addResourceHandler("/pollOptionImages/**")
                .addResourceLocations(POLL_OPTION_IMAGE_DIRECTORY_PATH);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true);
    }
}
