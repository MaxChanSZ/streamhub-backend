package com.fdmgroup.backend_streamhub.videostream.repository;

import com.fdmgroup.backend_streamhub.videostream.model.Series;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface ISeriesRepository extends JpaRepository<Series, Long> {
    List<Series> findBySeriesTitleContaining(String title);

    @Query(value = "SELECT s FROM Series s ORDER BY s.releaseDate DESC")
    List<Series> findTopByOrderByReleaseDateDesc(int limit);

    @Query("SELECT s FROM Series s ORDER BY s.rating DESC")
    List<Series> findTopRatedSeries(Pageable pageable);

}
