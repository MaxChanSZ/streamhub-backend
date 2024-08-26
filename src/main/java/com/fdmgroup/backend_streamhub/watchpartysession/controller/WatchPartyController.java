package com.fdmgroup.backend_streamhub.watchpartysession.controller;

import com.fdmgroup.backend_streamhub.watchpartysession.model.WatchParty;
import com.fdmgroup.backend_streamhub.watchpartysession.service.WatchPartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/watch-party")
public class WatchPartyController {

    @Autowired
    WatchPartyService watchPartyService;

    @PostMapping("/create")
    public ResponseEntity<WatchParty> createWatchParty(
            @RequestParam String partyName,
            @RequestParam Long accountID) {
        WatchParty watchParty = watchPartyService.createWatchParty(partyName, accountID);
        return ResponseEntity.ok(watchParty);
    }


}
