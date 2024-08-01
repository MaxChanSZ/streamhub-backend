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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RegistrationController {

    /**
     * Logger to monitor operational flow and facilitate troubleshooting.
     */
    private static final Logger registrationControllerLogger = LogManager.getLogger(RegistrationController.class);

    @Autowired
    private AccountRepository accountRepository;

    /**
     * Service for handling user registration operations.
     */
    @Autowired
    private RegistrationService registrationService;

    /**
     * Handles the GET request for the Registration page.
     *
     * @return The name of the view for the Registration page.
     */
    @GetMapping("/registration")
    public String showRegistration() {
        return "registration";
    }

    /**
     * Handles the POST request for the Registration page.
     *
     * @return  The name of the view for the Login page for successful registration,
     *          or the name of the view for the Registration page for unsuccessful registration.
     */
    @PostMapping("/registration")
    public String registrationAttempt(@RequestParam String username,
                                      @RequestParam String email,
                                      @RequestParam String password,
                                      RedirectAttributes redirectAttributes,
                                      Model model) {
        try {
            registrationControllerLogger.info("Registration attempt | Username: {}, Email Address: {}, Password: {}",
                                                username, email, password);
            registrationService.registerUser(username, email, password);
            if (accountRepository.findByUsername(username).isPresent()) {
                Account registeredAccount = accountRepository.findByUsername(username).get();
                registrationControllerLogger.info("Successful registration | {}", registeredAccount.toString());
            }
            redirectAttributes.addFlashAttribute("successfulRegistration", "Registration successful. Please log in.");
            return "redirect:/login";

        } catch (InvalidUsernameException e) {
            registrationControllerLogger.error("Unsuccessful registration attempt due to invalid username. Username: {}", username);
            model.addAttribute("error", "Invalid username entered. Please enter a valid username.");
            return "registration";

        } catch (InvalidEmailAddressException e) {
            registrationControllerLogger.error("Unsuccessful registration attempt due to invalid email address. Email address: {}", email);
            model.addAttribute("error", "Invalid email address entered. Please enter a valid email address.");
            return "registration";

        } catch (InvalidPasswordException e) {
            registrationControllerLogger.error("Unsuccessful registration attempt due to invalid password. Password: {}", password);
            model.addAttribute("error", "Invalid password entered. Please enter a valid password.");
            return "registration";

        } catch (UnavailableEmailAddressException e) {
            registrationControllerLogger.error("Unsuccessful registration attempt as email address has been registered to another user. Email address: {}", email);
            model.addAttribute("error", "Email address entered is unavailable. Please enter another email address.");
            return "registration";

        } catch (UnavailablePasswordException e) {
            registrationControllerLogger.error("Unsuccessful registration attempt as password has been registered to another user. Password: {}", password);
            model.addAttribute("error", "Password entered is unavailable. Please enter another password.");
            return "registration";

        } catch (UnavailableUsernameException e) {
            registrationControllerLogger.error("Unsuccessful registration attempt as username has been registered to another user. Username: {}", username);
            model.addAttribute("error", "Username entered is unavailable. Please enter another username.");
            return "registration";

        } catch (Exception e) {
            registrationControllerLogger.fatal("Unsuccessful registration attempt due to invalid an unexpected error.");
            model.addAttribute("error", "An unexpected error occurred. Please try again later.");
            return "registration";
        }

    }

}
