/**
 * Controller class handling requests related to the Registration page.
 */

package com.fdmgroup.backend_streamhub.authenticate.controller;

import com.fdmgroup.backend_streamhub.authenticate.exceptions.*;
import com.fdmgroup.backend_streamhub.authenticate.model.Account;
import com.fdmgroup.backend_streamhub.authenticate.repository.AccountRepository;
import com.fdmgroup.backend_streamhub.authenticate.service.RegistrationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class RegistrationController {

    /**
     * Logger to monitor operational flow and facilitate troubleshooting.
     */
    private static final Logger registrationControllerLogger = LogManager.getLogger(RegistrationController.class);

    /**
     * Repository interface for managing {@link Account} entities.
     * This repository provides CRUD operations for {@link Account} entities.
     */
    @Autowired
    private AccountRepository accountRepository;

    /**
     * Service for handling user registration operations.
     */
    @Autowired
    private RegistrationService registrationService;

    /**
     * Handles the GET request to the registration endpoint.
     * This method returns a simple response indicating that the Registration page is available.
     *
     * @return A {@code ResponseEntity} containing a message indicating the Registration page status.
     */
    @GetMapping("/registration")
    public ResponseEntity<String> showRegistration() {
        return ResponseEntity.ok("Registration page.");
    }

    /**
     * Handles POST requests to the registration endpoint.
     *
     * @param accountRegistrationRequest The {@code Account} object containing user registration data.
     * @return A {@code ResponseEntity} with a status and message indicating the result of the registration attempt.
     */
    @PostMapping("/registration")
    public ResponseEntity<String> registrationAttempt(@RequestBody Account accountRegistrationRequest) {
        String username = accountRegistrationRequest.getUsername();
        String email = accountRegistrationRequest.getEmail();
        String password = accountRegistrationRequest.getPassword();
        try {
            registrationControllerLogger.info("Registration attempt | Username: {}, Email Address: {}, Password: {}",
                                                username, email, password);
            registrationService.registerUser(username, email, password);
            if (accountRepository.findByUsername(username).isPresent()) {
                Account registeredAccount = accountRepository.findByUsername(username).get();
                registrationControllerLogger.info("Successful registration | {}", registeredAccount.toString());
            }
            return ResponseEntity.status(HttpStatus.CREATED).body("Registration successful. Please log in.");

        } catch (InvalidUsernameException e) {
            registrationControllerLogger.error("Unsuccessful registration attempt due to invalid username. Username: {}", username);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid username entered.");

        } catch (InvalidEmailAddressException e) {
            registrationControllerLogger.error("Unsuccessful registration attempt due to invalid email address. Email address: {}", email);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid email address entered.");

        } catch (InvalidPasswordException e) {
            registrationControllerLogger.error("Unsuccessful registration attempt due to invalid password. Password: {}", password);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid password entered.");

        } catch (UnavailableEmailAddressException e) {
            registrationControllerLogger.error("Unsuccessful registration attempt as email address has been registered to another user. Email address: {}", email);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email address entered is unavailable.");

        } catch (UnavailablePasswordException e) {
            registrationControllerLogger.error("Unsuccessful registration attempt as password has been registered to another user. Password: {}", password);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password entered is unavailable.");

        } catch (UnavailableUsernameException e) {
            registrationControllerLogger.error("Unsuccessful registration attempt as username has been registered to another user. Username: {}", username);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username entered is unavailable.");

        } catch (Exception e) {
            registrationControllerLogger.fatal("Unsuccessful registration attempt due to invalid an unexpected error.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error occurred.");
        }

    }

}
