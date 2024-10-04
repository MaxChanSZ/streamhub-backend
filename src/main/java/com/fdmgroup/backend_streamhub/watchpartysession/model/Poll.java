package com.fdmgroup.backend_streamhub.watchpartysession.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Poll {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String question;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "watchparty_id")
    @JsonIgnore
    private WatchParty watchParty;

    @OneToMany(mappedBy = "poll")
    @JsonIgnore
    private List<PollOption> pollOptions = new ArrayList<>();

    public long getId() {
        return id;
    }

    public void addPollOptions(PollOption pollOption) {
        pollOptions.add(pollOption);
    }

}