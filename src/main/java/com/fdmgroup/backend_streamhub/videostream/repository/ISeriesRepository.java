package com.fdmgroup.backend_streamhub.videostream.repository;

import com.fdmgroup.backend_streamhub.videostream.model.Series;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.FetchType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface ISeriesRepository extends JpaRepository<Series, Long> {
    @Query(value = "SELECT * FROM series s WHERE s.series_title LIKE CONCAT('%', ?1, '%') OR s.description LIKE CONCAT('%', ?2, '%')", nativeQuery = true)
    List<Series> findBySeriesTitleOrDescription(String title, String description);

    @Query(value = "SELECT s FROM Series s ORDER BY s.releaseDate DESC")
    List<Series> findTopByOrderByReleaseDateDesc(int limit);

    @Query("SELECT s FROM Series s ORDER BY s.rating DESC")
    List<Series> findTopRatedSeries(Pageable pageable);

    List<Series> findByCategory(String category);

    @ElementCollection(fetch= FetchType.EAGER)
    @Query("SELECT DISTINCT(s.category) FROM Series s")
    List<String> findUniqueCategories();

}
