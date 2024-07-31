package com.fdmgroup.backend_streamhub.authenticate.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    @GetMapping("/account/testing")
    public ResponseEntity<String> accTesting() {
        return new ResponseEntity<>("hello world", HttpStatus.OK);
    }
}
