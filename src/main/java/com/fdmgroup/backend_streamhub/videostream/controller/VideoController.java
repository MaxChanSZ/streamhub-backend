package com.fdmgroup.backend_streamhub.videostream.controller;

import com.fdmgroup.backend_streamhub.videostream.model.Video;
import com.fdmgroup.backend_streamhub.videostream.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/videos")
public class VideoController {
    //TODO: add methods to return a list of videos, as well as return the necessary
    // information for a single video when a request is received from the frontend

    private final String VIDEO_BASE_URL = "http://localhost:8080/";
    @Autowired
    VideoService videoService;

    @GetMapping
    private ResponseEntity<List<Video>> getVideos() {
        // TODO: Implement pagination of videos instead of returning all videos
        // TODO: Decide which properties of the video need to be sent over to the frontend
        return ResponseEntity.ok(videoService.findAllVideos());
    }

    @GetMapping("/{id}")
    private ResponseEntity<Video> getVideoById(@PathVariable("id") long videoId) {
        Optional<Video> videoOptional = videoService.findVideoById(videoId);
        if ( videoOptional.isEmpty() ) {
            return ResponseEntity.notFound().build();
        } else {
            Video video = videoOptional.get();
            video.setVideoURL(VIDEO_BASE_URL + video.getVideoURL());
            return  ResponseEntity.ok(video);
        }
    }
}
