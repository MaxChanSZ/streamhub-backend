/**
 * Controller class handling requests related to the Index page.
 */

package com.fdmgroup.backend_streamhub.authenticate.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    /**
     * Handles the GET request for the Index page.
     *
     * @return The name of the view for the Index page.
     */
    @GetMapping("/")
    public String showIndex() {
        return "index";
    }

}
