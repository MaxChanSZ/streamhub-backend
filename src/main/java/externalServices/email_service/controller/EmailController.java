package externalServices.email_service.controller;


import externalServices.email_service.service.EmailServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class EmailController {

    @Autowired
    private final EmailServiceImpl emailService;

    public EmailController(EmailServiceImpl emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/email/sendSimpleEmail/test")
    public void sendEmail() {
        log.info("Email testing endpoint at externalServices - EmailController hit");
        emailService.sendSimpleMessage("wmaxchan@gmail.com", "Test Email", "This is a test email");
        log.info("Email Sent!");
    }

}
