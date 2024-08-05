/**
 * Controller class handling requests related to the Login page.
 */

package com.fdmgroup.backend_streamhub.authenticate.controller;

import com.fdmgroup.backend_streamhub.authenticate.dto.LoginRequest;
import com.fdmgroup.backend_streamhub.authenticate.exceptions.IncorrectUsernameOrEmailAddressException;
import com.fdmgroup.backend_streamhub.authenticate.exceptions.IncorrectPasswordException;
import com.fdmgroup.backend_streamhub.authenticate.service.LoginService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {

    /**
     * Logger to monitor operational flow and facilitate troubleshooting.
     */
    private static final Logger loginControllerLogger = LogManager.getLogger(LoginController.class);

    /**
     * Service for handling user registration operations.
     */
    @Autowired
    private LoginService loginService;

    /**
     * Handles the POST request to the login endpoint.
     *
     * @param loginRequest The {@code LoginRequest} object containing user login data.
     * @return A {@code ResponseEntity} with a status and message indicating the result of the login attempt.
     */
    public ResponseEntity<String> registrationAttempt(@RequestBody LoginRequest loginRequest) {
        try {
            loginControllerLogger.info("Login attempt | {}", loginRequest.toString());
            loginService.loginUser(loginRequest);
            return ResponseEntity.status(HttpStatus.OK).body("Login successful.");

        } catch (IncorrectUsernameOrEmailAddressException e) {
            loginControllerLogger.error("Unsuccessful login due to incorrect username or email address entered.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid username or email address entered.");

        } catch (IncorrectPasswordException e) {
            loginControllerLogger.error("Unsuccessful login due to incorrect password entered.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid password entered.");

        } catch (Exception e) {
            loginControllerLogger.fatal("Unsuccessful registration attempt due to invalid an unexpected error.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error occurred.");
        }
    }

}
