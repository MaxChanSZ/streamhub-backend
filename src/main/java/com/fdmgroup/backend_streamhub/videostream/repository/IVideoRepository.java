package com.fdmgroup.backend_streamhub.videostream.repository;

import com.fdmgroup.backend_streamhub.videostream.model.Series;
import com.fdmgroup.backend_streamhub.videostream.model.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IVideoRepository extends JpaRepository<Video, Long> {
    List<Video> findBySeriesIs(Series series);
}
