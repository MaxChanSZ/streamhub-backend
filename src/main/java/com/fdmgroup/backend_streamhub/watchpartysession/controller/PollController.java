package com.fdmgroup.backend_streamhub.watchpartysession.controller;

import com.fdmgroup.backend_streamhub.watchpartysession.dto.CreatePollOptionRequest;
import com.fdmgroup.backend_streamhub.watchpartysession.dto.CreatePollRequest;
import com.fdmgroup.backend_streamhub.watchpartysession.model.Poll;
import com.fdmgroup.backend_streamhub.watchpartysession.model.PollOption;
import com.fdmgroup.backend_streamhub.watchpartysession.service.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/poll")
public class PollController {

    @Autowired PollService pollService;

    @PostMapping(path="/create")
    public ResponseEntity<Poll> createPoll(
            @RequestBody CreatePollRequest createPollRequest) {
        // create poll and options
        Poll poll = null;
        if (createPollRequest != null) {
            // create poll first
            poll = pollService.createPoll(
                    createPollRequest.getWatchPartyID(),
                    createPollRequest.getQuestion()
            );
            if (poll != null) {
                // then create options for the poll
                for (CreatePollOptionRequest request : createPollRequest.getPollOptionRequests()) {
                    //String uniqueFileName = generateImageUrl(request.getImage(), request.getImageOptionUrl());
                    // save option in db
                    PollOption pollOption = pollService.createOption(
                            poll.getId(),
                            request.getValue(),
                            request.getDescription(),
                            request.getFileName()
                    );
                    poll.addPollOptions(pollOption);
                }
            }
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(poll);
    }

    @PostMapping("/get-poll-options-by-poll")
    public ResponseEntity<List<PollOption>> getPollOptionsListByPoll(@RequestParam("pollId") long pollId) {
       List<PollOption> pollOptionsList = pollService.getPollOptionsByPoll(pollId);
       return ResponseEntity.status(HttpStatus.CREATED).body(pollOptionsList);
    }

    @PostMapping("/get-poll-by-watchparty")
    public ResponseEntity<Poll> getPollByWatchParty(@RequestParam("watchPartyId") long watchpartyId) {
        Poll poll = pollService.getPollByWatchPartyId(watchpartyId);
        return ResponseEntity.status(HttpStatus.CREATED).body(poll);
    }

}
