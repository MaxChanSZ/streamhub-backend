package com.fdmgroup.backend_streamhub.videostream.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String videoTitle;
    private String description;
    private int episode;
    private LocalDate releaseDate;
    private long durationSecond;
    private String thumbnailURL;
    private String videoURL;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "series_id")
    private Series series;

}
