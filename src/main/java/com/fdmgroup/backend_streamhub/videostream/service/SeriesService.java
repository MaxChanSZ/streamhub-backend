package com.fdmgroup.backend_streamhub.videostream.service;

import com.fdmgroup.backend_streamhub.videostream.model.Series;
import com.fdmgroup.backend_streamhub.videostream.repository.ISeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<Series> findSeriesByTitle(String title) {
        return seriesRepository.findBySeriesTitleContaining(title);
    }

    public Optional<Series> findSeries(long id) {
        return seriesRepository.findById(id);
    }
    public List<Series> findNewestSeries(int limit) {
        return seriesRepository.findTopByOrderByReleaseDateDesc(limit);
    }
}
