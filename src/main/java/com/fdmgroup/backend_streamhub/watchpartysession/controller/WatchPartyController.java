package com.fdmgroup.backend_streamhub.watchpartysession.controller;

import com.fdmgroup.backend_streamhub.authenticate.service.TokenService;
import com.fdmgroup.backend_streamhub.watchpartysession.dto.CreateWatchPartyRequest;
import com.fdmgroup.backend_streamhub.watchpartysession.dto.JoinWatchPartyRequest;
import com.fdmgroup.backend_streamhub.watchpartysession.dto.JoinWatchPartyResponse;
import com.fdmgroup.backend_streamhub.watchpartysession.model.WatchParty;
import com.fdmgroup.backend_streamhub.watchpartysession.service.WatchPartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/watch-party")
public class WatchPartyController {

  @Autowired WatchPartyService watchPartyService;

  @Autowired
  TokenService tokenService;

  @PostMapping("/create")
  public ResponseEntity<WatchParty> createWatchParty(
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

    return ResponseEntity.status(HttpStatus.CREATED).body(watchParty);
  }

  @PostMapping("/join")
  public ResponseEntity<?> joinWatchParty(
          @RequestBody JoinWatchPartyRequest joinRequest
  ){
    String code = joinRequest.getCode();
    String password = joinRequest.getPassword();

    System.out.println("Code is " + code);

    Optional<WatchParty> watchPartyOptional = watchPartyService.findByCode(code);

    if ( watchPartyOptional.isEmpty() ) {
      return ResponseEntity.notFound().build();
    }

    WatchParty watchParty = watchPartyOptional.get();

    String actualPassword = watchParty.getPassword();

    if ( !password.equals(actualPassword) ) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    // generate a token which contains the watch party information and return it to the user
    String token = tokenService.generateToken(code);

    JoinWatchPartyResponse response = new JoinWatchPartyResponse();
    response.setToken(token);
    response.setHost(false);
    // response.setVideoSource(watchParty.getVideo().getVideoURL());
    response.setRoomId(code);

    return ResponseEntity.ok(response);
  }
}
