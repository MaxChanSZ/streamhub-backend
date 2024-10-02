package externalServices.email_service.controller;

import externalServices.email_service.dto.WatchPartyInvitationRequest;
import externalServices.email_service.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class EmailController {

    private final EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/api/send-watch-party-email")
    public ResponseEntity<String> sendWatchPartyInvitations(@RequestBody WatchPartyInvitationRequest request) {
        log.info("Received request to send watch party invitations");
        try {
            for (String email : request.getEmailAddresses()) {
                emailService.sendSimpleMessage(email, request.getSubject(), request.getBody());
            }
            log.info("Watch party invitations sent successfully");
            return ResponseEntity.ok("Invitations sent successfully");
        } catch (Exception e) {
            log.error("Error sending watch party invitations", e);
            return ResponseEntity.internalServerError().body("Failed to send invitations");
        }
    }
}




