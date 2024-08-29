package com.fdmgroup.backend_streamhub.apigateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/authenticate")
@CrossOrigin(origins = "http://localhost:3000")
@Slf4j
@ComponentScan(basePackages = {"com.fdmgroup.backend_streamhub", "externalServices"})
public class AuthenticateController {

    @GetMapping("/call-acc-controller")
    public ResponseEntity<String> authTesting() {
        try {
            String uri = "http://localhost:8080/account/testing";
            RestTemplate restTemplate = new RestTemplate();
            String result = restTemplate.getForObject(uri, String.class);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error!, Please try again", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/email/sendSimpleEmail")
    public void emailTesting() {
        try {
            log.info("Email testing endpoint at AuthenticateController hit");
            String uri = "http://localhost:8080/email/sendSimpleEmail/test";
        } catch (Exception e) {
            e.printStackTrace();
            log.info("Email testing failed, try again.");
        }
    }
}
