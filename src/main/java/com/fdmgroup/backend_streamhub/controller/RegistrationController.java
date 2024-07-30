/**
 * Controller class handling requests related to the Registration page.
 */

package com.fdmgroup.backend_streamhub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegistrationController {

    /**
     * Handles the GET request for the Registration page.
     *
     * @return The name of the view for the Registration page.
     */
    @GetMapping("/registration")
    public String showRegistration() {
        return "registration";
    }
    
}
