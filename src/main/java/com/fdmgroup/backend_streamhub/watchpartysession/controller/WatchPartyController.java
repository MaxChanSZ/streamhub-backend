package com.fdmgroup.backend_streamhub.watchpartysession.controller;

import com.fdmgroup.backend_streamhub.watchpartysession.dto.CreateWatchPartyRequest;
import com.fdmgroup.backend_streamhub.watchpartysession.model.WatchParty;
import com.fdmgroup.backend_streamhub.watchpartysession.service.WatchPartyService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/watch-party")
public class WatchPartyController {

    @Autowired
    WatchPartyService watchPartyService;

    @GetMapping("/create")
    public ResponseEntity<WatchParty> createWatchParty(@RequestBody CreateWatchPartyRequest createWatchPartyRequest) {
        System.out.println(createWatchPartyRequest.getPartyName());
        System.out.println(createWatchPartyRequest.getAccountID());
        WatchParty watchParty = watchPartyService.createWatchParty(createWatchPartyRequest.getPartyName(), createWatchPartyRequest.getAccountID());
        //return ResponseEntity.ok(watchParty);
        return ResponseEntity.status(HttpStatus.CREATED).body(watchParty);
    }


}
