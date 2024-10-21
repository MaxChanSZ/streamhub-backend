package com.fdmgroup.backend_streamhub.watchpartysession.controller;

import com.fdmgroup.backend_streamhub.watchpartysession.dto.*;
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
                    createPollRequest.getPartyCode(),
                    createPollRequest.getQuestion()
            );
            if (poll != null) {
                // then create options for the poll
                for (PollOptionRequest request : createPollRequest.getPollOptionRequests()) {
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

    @PostMapping("/get-watchparty-poll-by-code")
    public ResponseEntity<WatchPartyPollResponse> getWatchPartyPollByCode(@RequestParam("code") String code, @RequestParam("userId") long userId)  {
        WatchPartyPollResponse response = pollService.getWatchPartyPollResponse(code, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/update")
    public ResponseEntity<Poll> updatePoll(
            @RequestBody UpdatePollRequest updatePollRequest) {

        // update poll question
        Poll updatedPoll = pollService.updatePoll(
                updatePollRequest.getAccountID(),
                updatePollRequest.getPollID(),
                updatePollRequest.getQuestion()
        );

        List<PollOption> currentPollOptions = pollService.getPollOptionsByPoll(updatePollRequest.getPollID());
        int updatedOptionsCount = 0;

        // update poll options
        for(PollOptionRequest request: updatePollRequest.getPollOptionRequests()) {
            updatedOptionsCount++;
            if(updatedOptionsCount > currentPollOptions.size()) {
                PollOption newOption = pollService.createOption(
                        updatePollRequest.getPollID(),
                        request.getValue(),
                        request.getDescription(),
                        request.getFileName()
                );
            } else {
                PollOption updatedOption = pollService.updateOption(
                        updatePollRequest.getAccountID(),
                        request.getPollOptionID(),
                        request.getValue(),
                        request.getDescription(),
                        request.getFileName()
                );
            }
        }

        // remove the remaining poll options if the new option list size is smaller
        if (updatedOptionsCount < currentPollOptions.size()) {
            List<PollOption> optionsToRemove = currentPollOptions.subList(updatedOptionsCount, currentPollOptions.size());
            for(PollOption option: optionsToRemove) {
                pollService.deletePollOption(option.getId());
            }
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(updatedPoll);
    }
}
