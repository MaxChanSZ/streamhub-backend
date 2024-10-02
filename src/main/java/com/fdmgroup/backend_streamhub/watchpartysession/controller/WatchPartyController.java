package com.fdmgroup.backend_streamhub.watchpartysession.controller;

import com.fdmgroup.backend_streamhub.watchpartysession.dto.CreateWatchPartyRequest;
import com.fdmgroup.backend_streamhub.watchpartysession.model.WatchParty;
import com.fdmgroup.backend_streamhub.watchpartysession.service.WatchPartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/watch-party")
public class WatchPartyController {

  @Autowired
  WatchPartyService watchPartyService;

  @PostMapping("/create")
  public ResponseEntity<WatchParty> createWatchParty(
          @RequestBody CreateWatchPartyRequest createWatchPartyRequest) {
    WatchParty watchParty = watchPartyService.createWatchParty(
            createWatchPartyRequest.getPartyName(),
            createWatchPartyRequest.getAccountID(),
            createWatchPartyRequest.getScheduledDate(),
            createWatchPartyRequest.getScheduledTime()
    );
    return ResponseEntity.status(HttpStatus.CREATED).body(watchParty);
  }

  @GetMapping("/get/{userId}")
  public ResponseEntity<List<WatchParty>> getWatchPartiesByUserId(@PathVariable Long userId) {
    List<WatchParty> watchParties = watchPartyService.getWatchPartiesByUserId(userId);
    return ResponseEntity.ok(watchParties);
  }

  @GetMapping("/get")
  public ResponseEntity<List<WatchParty>> getAllWatchParties() {
    List<WatchParty> watchParties = watchPartyService.getAllWatchParties();
    return ResponseEntity.ok(watchParties);
  }
}

