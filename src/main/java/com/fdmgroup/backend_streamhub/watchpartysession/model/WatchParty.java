package com.fdmgroup.backend_streamhub.watchpartysession.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fdmgroup.backend_streamhub.videostream.model.Video;
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
public class WatchParty {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String partyName;
    private LocalDate createdDate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "video_id")
    @JsonIgnore
    private Video video;

}