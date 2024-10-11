package com.fdmgroup.backend_streamhub.watchpartysession.controller;

import com.fdmgroup.backend_streamhub.authenticate.service.TokenService;
import com.fdmgroup.backend_streamhub.watchpartysession.dto.CreateWatchPartyRequest;
import com.fdmgroup.backend_streamhub.watchpartysession.dto.CreateWatchPartyResponse;
import com.fdmgroup.backend_streamhub.watchpartysession.dto.JoinWatchPartyRequest;
import com.fdmgroup.backend_streamhub.watchpartysession.dto.JoinWatchPartyResponse;
import com.fdmgroup.backend_streamhub.watchpartysession.model.WatchParty;
import com.fdmgroup.backend_streamhub.watchpartysession.service.WatchPartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.List;


@RestController
@RequestMapping("/api/watch-party")
public class WatchPartyController {

  @Autowired
  WatchPartyService watchPartyService;

  @Autowired
  TokenService tokenService;

  private final String VIDEO_BASE_URL = "http://localhost:8080/encoded/";

  @PostMapping("/create")
  public ResponseEntity<CreateWatchPartyResponse> createWatchParty(
          @RequestBody CreateWatchPartyRequest createWatchPartyRequest) {

//    System.out.println(createWatchPartyRequest.getPartyName());
//    System.out.println(createWatchPartyRequest.getAccountID());
//    System.out.println(createWatchPartyRequest.getPassword());

    WatchParty watchParty = watchPartyService.createWatchParty(
            createWatchPartyRequest.getPartyName(),
            createWatchPartyRequest.getPassword(),
            createWatchPartyRequest.getAccountID(),
            createWatchPartyRequest.getScheduledDate(),
            createWatchPartyRequest.getScheduledTime()
    );

    // generate a token with host privileges to send to the client
    String token = tokenService.generateToken(watchParty.getCode(), "host");

    CreateWatchPartyResponse response = new CreateWatchPartyResponse();
    response.setHost(true);
    response.setToken(token);
    response.setCode(watchParty.getCode());
    response.setVideoSource(VIDEO_BASE_URL + watchParty.getVideo().getVideoURL());

    System.out.println(response.toString());

    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @PostMapping("/join")
  public ResponseEntity<?> joinWatchParty(
          @RequestBody JoinWatchPartyRequest joinRequest
  ){
    String code = joinRequest.getCode();
    String password = joinRequest.getPassword();

    Optional<WatchParty> watchPartyOptional = watchPartyService.findByCode(code);

    if ( watchPartyOptional.isEmpty() ) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid party code");
    }

    WatchParty watchParty = watchPartyOptional.get();

    String actualPassword = watchParty.getPassword();

    if ( !password.equals(actualPassword) ) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect password for party code");
    }

    // generate a token which contains the watch party information and return it to the user
    String token = tokenService.generateToken(code, "guest");

    JoinWatchPartyResponse response = new JoinWatchPartyResponse();
    response.setToken(token);
    response.setHost(false);
    response.setVideoSource(VIDEO_BASE_URL + watchParty.getVideo().getVideoURL());
    response.setRoomId(code);
    System.out.println(response.toString());

    return ResponseEntity.ok(response);
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

  @GetMapping("/get/with-poll")
  public ResponseEntity<List<WatchParty>> getAllWatchPartiesWithPoll() {
    List<WatchParty> watchParties = watchPartyService.getAllWatchPartiesWithPoll();
    return ResponseEntity.ok(watchParties);
  }
}
