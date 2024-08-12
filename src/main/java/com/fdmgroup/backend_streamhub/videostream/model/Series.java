package com.fdmgroup.backend_streamhub.videostream.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Series {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String seriesTitle;
    private String description;
    private LocalDate releaseDate;
    private String thumbnailURL;
    private String rating;
    private String cast;

    @OneToMany(mappedBy = "series")
    @JsonIgnore
    private List<Video> videos = new ArrayList<>();

    public void addVideos(Video video) {
        videos.add(video);
    }
}
