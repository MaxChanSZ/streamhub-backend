package externalServices.ai_service.controller;

import externalServices.ai_service.service.VideoChatbotService;
import externalServices.ai_service.repository.ChatRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/videochatbot")
public class VideoChatbotController {

    @Autowired
    private VideoChatbotService videoChatbotService;

    @PostMapping("/chat")
    public ResponseEntity<String> chat(@RequestBody ChatRequest chatRequest) {
        try {
            String response = videoChatbotService.generateResponse(chatRequest.getUserInput());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error processing request: " + e.getMessage());
        }
    }
}



