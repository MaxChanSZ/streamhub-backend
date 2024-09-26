package com.fdmgroup.backend_streamhub.watchpartysession.controller;

import com.fdmgroup.backend_streamhub.watchpartysession.dto.CreateWatchPartyRequest;
import com.fdmgroup.backend_streamhub.watchpartysession.dto.CreatePollRequest;
import com.fdmgroup.backend_streamhub.watchpartysession.dto.CreatePollOptionRequest;
import com.fdmgroup.backend_streamhub.watchpartysession.model.PollOption;
import com.fdmgroup.backend_streamhub.watchpartysession.model.Poll;
import com.fdmgroup.backend_streamhub.watchpartysession.model.WatchParty;
import com.fdmgroup.backend_streamhub.watchpartysession.service.WatchPartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/watch-party")
public class WatchPartyController {

  @Autowired WatchPartyService watchPartyService;

  @PostMapping("/create")
  public ResponseEntity<WatchParty> createWatchParty(@RequestBody CreateWatchPartyRequest createWatchPartyRequest) {
    System.out.println(createWatchPartyRequest.getPartyName());
    System.out.println(createWatchPartyRequest.getAccountID());
    WatchParty watchParty = watchPartyService.createWatchParty(
            createWatchPartyRequest.getPartyName(),
            createWatchPartyRequest.getAccountID()
    );

    // return ResponseEntity.ok(watchParty);
    return ResponseEntity.status(HttpStatus.CREATED).body(watchParty);
  }

  @PostMapping("/create-poll")
  public ResponseEntity<Poll> createPoll(@RequestBody CreatePollRequest createPollRequest) {
      // create poll and options
      Poll poll = null;
      if (createPollRequest != null) {
          poll = watchPartyService.createPoll(
                  createPollRequest.getWatchPartyID(),
                  createPollRequest.getQuestion()
          );
          if (poll != null) {
              for (CreatePollOptionRequest request : createPollRequest.getPollOptionRequests()) {
                  PollOption pollOption = watchPartyService.createOption(
                          poll.getId(),
                          request.getValue()
                  );
                  poll.addPollOptions(pollOption);
              }
          }
      }

      // return ResponseEntity.ok(watchParty);
      return ResponseEntity.status(HttpStatus.CREATED).body(poll);
  }
}
