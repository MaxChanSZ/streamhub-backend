package com.fdmgroup.backend_streamhub.videostream.service;

import com.fdmgroup.backend_streamhub.videostream.model.Video;
import com.fdmgroup.backend_streamhub.videostream.repository.IVideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VideoService {

    @Autowired
    IVideoRepository videoRepository;

    public List<Video> findAllVideos() {
        return videoRepository.findAll();
    }

    public Optional<Video> findVideoById(long id) {
        return videoRepository.findById(id);
    }
}
