package externalServices.ai_service.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

@Service
public class VideoChatbotService {

    @Value("${google.api.secret}")
    private String googleApiKey;

    private final String GEMINI_API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent";
    private final String PRE_PROMPT = "You are an AI assistant that analyzes Steamboat Willie. Make it short and concise " +
            "Respond to the user's input, but make sure your response is always about Steamboat Willie. " ;

    public String generateResponse(String userInput) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> requestBody = new HashMap<>();
        List<Map<String, Object>> contents = new ArrayList<>();

        // Add pre-prompt with role "model"
        Map<String, Object> prePromptContent = new HashMap<>();
        prePromptContent.put("role", "model");
        Map<String, String> prePromptPart = new HashMap<>();
        prePromptPart.put("text", PRE_PROMPT);
        prePromptContent.put("parts", new Object[]{prePromptPart});
        contents.add(prePromptContent);

        // Add user input with role "user"
        Map<String, Object> userContent = new HashMap<>();
        userContent.put("role", "user");
        Map<String, String> userPart = new HashMap<>();
        userPart.put("text", userInput);
        userContent.put("parts", new Object[]{userPart});
        contents.add(userContent);

        requestBody.put("contents", contents);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        String url = GEMINI_API_URL + "?key=" + googleApiKey;

        try {
            Map<String, Object> response = restTemplate.postForObject(url, request, Map.class);
            return extractGeneratedText(response);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }


    private String extractGeneratedText(Map<String, Object> response) {
        if (response == null || !response.containsKey("candidates")) {
            return "Error: Unexpected response format";
        }

        List<Map<String, Object>> candidates = (List<Map<String, Object>>) response.get("candidates");
        if (candidates.isEmpty()) {
            return "Error: No response generated";
        }

        Map<String, Object> firstCandidate = candidates.get(0);
        if (!firstCandidate.containsKey("content")) {
            return "Error: Response content not found";
        }

        Map<String, Object> content = (Map<String, Object>) firstCandidate.get("content");
        List<Map<String, Object>> parts = (List<Map<String, Object>>) content.get("parts");
        if (parts.isEmpty()) {
            return "Error: Response parts not found";
        }

        return (String) parts.get(0).get("text");
    }
}



