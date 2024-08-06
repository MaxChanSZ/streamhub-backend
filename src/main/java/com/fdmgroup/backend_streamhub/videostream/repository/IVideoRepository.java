package com.fdmgroup.backend_streamhub.videostream.repository;

import com.fdmgroup.backend_streamhub.videostream.model.Video;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IVideoRepository extends JpaRepository<Video, Long> {

}
