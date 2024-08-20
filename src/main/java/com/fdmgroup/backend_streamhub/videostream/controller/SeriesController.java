package com.fdmgroup.backend_streamhub.videostream.controller;

import com.fdmgroup.backend_streamhub.videostream.model.Series;
import com.fdmgroup.backend_streamhub.videostream.model.Video;
import com.fdmgroup.backend_streamhub.videostream.service.SeriesService;
import com.fdmgroup.backend_streamhub.videostream.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/series")
public class SeriesController {

    private final String VIDEO_BASE_URL = "http://localhost:8080/encoded/";
    private final String THUMBNAIL_BASE_URL = "http://localhost:8080/thumbnails/";

    @Autowired
    SeriesService seriesService;

    @Autowired
    VideoService videoService;

    @GetMapping
    private ResponseEntity<List<Series>> getSeries(@RequestParam(name = "title", required = false) String title) {
        // TODO: Implement pagination of series instead of returning all series
        // TODO: Decide which properties of the video need to be sent over to the frontend
        List<Series> seriesList;
        if ( title == null ) {
            seriesList = seriesService.findAllSeries();
        } else {
            seriesList = seriesService.findSeriesByTitle(title);
        }
        seriesList.forEach(series -> series.setThumbnailURL( THUMBNAIL_BASE_URL + series.getThumbnailURL() ));
        return ResponseEntity.ok(seriesList);
    }

    @GetMapping("/all-videos/{seriesId}")
    private ResponseEntity<List<Video>> getVideosInSeries(@PathVariable long seriesId) {
        // TODO: Implement pagination of videos instead of returning all videos in the series
        // TODO: Decide which properties of the video need to be sent over to the frontend
        Optional<Series> series = seriesService.findSeries(seriesId);

        if (series.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<Video> videoList = videoService.findAllVideosInSeries(series.get());
        videoList.forEach( video -> video.setThumbnailURL( THUMBNAIL_BASE_URL + video.getThumbnailURL() ));

        return ResponseEntity.ok(videoList);
    }

    @GetMapping("/newest")
    private ResponseEntity<List<Series>> getNewestSeries() {
        List<Series> seriesList = seriesService.findNewestSeries(8);
        seriesList.forEach(series -> series.setThumbnailURL(THUMBNAIL_BASE_URL + series.getThumbnailURL()));
        return ResponseEntity.ok(seriesList);
    }
    @GetMapping("/top-rated")
    private ResponseEntity<List<Series>> getTopRatedSeries() {
        List<Series> seriesList = seriesService.findTopRatedSeries(5);
        seriesList.forEach(series -> series.setThumbnailURL(THUMBNAIL_BASE_URL + series.getThumbnailURL()));
        return ResponseEntity.ok(seriesList);
    }
}
