package com.fdmgroup.backend_streamhub.videostream.controller;

import com.fdmgroup.backend_streamhub.videostream.model.Video;
import com.fdmgroup.backend_streamhub.videostream.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/videos")
public class VideoController {
    //TODO: add methods to return a list of videos, as well as return the necessary
    // information for a single video when a request is received from the frontend

    @Autowired
    VideoService videoService;

    @GetMapping
    private ResponseEntity<List<Video>> getVideos() {
        // TODO: Implement pagination of videos instead of returning all videos
        // TODO: Decide which properties of the video need to be sent over to the frontend
        List<Video> videos = videoService.findAllVideos();

        return ResponseEntity.ok(videos);
    }

    @GetMapping("/{id}")
    private ResponseEntity<Video> getVideoById(@PathVariable("id") long requestedId) {
        Optional<Video> videoOptional = videoService.findVideoById(requestedId);
        return videoOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


}
