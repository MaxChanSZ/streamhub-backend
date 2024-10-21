package com.fdmgroup.backend_streamhub.videostream.service;

import com.fdmgroup.backend_streamhub.videostream.model.Series;
import com.fdmgroup.backend_streamhub.videostream.repository.ISeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SeriesService {

    @Autowired
    ISeriesRepository seriesRepository;

    public List<Series> findAllSeries() {
        return seriesRepository.findAll();
    }

    public List<Series> findSeriesByTitleOrDescription(String title, String description) {
        return seriesRepository.findBySeriesTitleOrDescription(title, description);
    }

    public List<Series> findSeriesByCategory(String category) {
        return seriesRepository.findByCategory(category);
    }

    public List<String> findUniqueCategories() {
        return seriesRepository.findUniqueCategories();
    }

    public Optional<Series> findSeries(long id) {
        return seriesRepository.findById(id);
    }
    public List<Series> findNewestSeries(int limit) {
        return seriesRepository.findTopByOrderByReleaseDateDesc(limit);
    }

    public List<Series> findTopRatedSeries(int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return seriesRepository.findTopRatedSeries(pageable);
    }
}
