package com.fdmgroup.backend_streamhub.apigateway;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class TestController {

    @GetMapping("/testing/api1")
    public ResponseEntity<String> testing1() {
        return new ResponseEntity<>("hello world", HttpStatus.OK);
    }
}
