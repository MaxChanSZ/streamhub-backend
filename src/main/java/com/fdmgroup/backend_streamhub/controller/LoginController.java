/**
 * Controller class handling requests related to the Login page.
 */

package com.fdmgroup.backend_streamhub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    /**
     * Handles the GET request for the Login page.
     *
     * @return The name of the view for the Login page.
     */
    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }

}
