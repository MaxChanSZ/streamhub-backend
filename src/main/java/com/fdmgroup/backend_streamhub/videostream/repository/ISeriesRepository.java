package com.fdmgroup.backend_streamhub.videostream.repository;

import com.fdmgroup.backend_streamhub.videostream.model.Series;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ISeriesRepository extends JpaRepository<Series, Long> {
    List<Series> findBySeriesTitleContaining(String title);
}
