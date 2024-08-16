package com.fdmgroup.backend_streamhub.authenticate.controller;

import com.fdmgroup.backend_streamhub.authenticate.dto.LoginRequest;
import com.fdmgroup.backend_streamhub.authenticate.dto.LoginResponse;
import com.fdmgroup.backend_streamhub.authenticate.dto.RegistrationRequest;
import com.fdmgroup.backend_streamhub.authenticate.dto.RegistrationResponse;
import com.fdmgroup.backend_streamhub.authenticate.exceptions.*;
import com.fdmgroup.backend_streamhub.authenticate.model.Account;
import com.fdmgroup.backend_streamhub.authenticate.ApiIndex.ApiResponse;
import com.fdmgroup.backend_streamhub.authenticate.ApiIndex.ApiResponseAccount;
import com.fdmgroup.backend_streamhub.authenticate.service.AccountService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }


    private static final Logger accountControllerLogger = LogManager.getLogger(AccountController.class);
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);

    @GetMapping("/testing")
    public ResponseEntity<String> accTesting() {
        return new ResponseEntity<>("hello world", HttpStatus.OK);
    }

    @GetMapping("/api")
    public ApiResponse apiHomeController(){
        ApiResponse response = new ApiResponse();
        response.setMessage("API Test Success");
        response.setStatusCode("200 OK");
        return response;
    }

    @PostMapping("/api/accounttest")
    public ApiResponseAccount AccountUpdateTest(@RequestBody Account inputAccount) {
        //Future TODO: to transition this into an account updating API. When this is called, it should take in an 'account' input and return an account + status code & message to let caller know account has been updated (or has failed)
        // Note: It should have verification logic. Will use accountService to do account creation/update, then update and return response accordingly.
        Account mockAccount = inputAccount;

        // TO ADD: Account creation/update logic.
        //if success, return this code below:
        ApiResponseAccount response = new ApiResponseAccount();
        response.setAccount = mockAccount;
        response.setMessage("Account function completed");
        response.setStatusCode("200 OK");


//        If account creation/update failed:
//        ApiResponseAccount response = new ApiResponseAccount();
//        response.setAccount = mockAccount;
//        response.setMessage("Account function failed");
//        response.setStatusCode(/*"UPDATE THIS BASED ON ERROR. EG - 404 BAD REQUEST / 401 UNAUTHORIZED"*/);

        return response;
    }

    @PutMapping("/account/api/update")
    public ApiResponseAccount AccountUpdate(@RequestBody Account inputAccount) {
        // TO ADD: Account creation/update logic.
        Account updatedAccount = accountService.updateAccount(inputAccount);

        ApiResponseAccount response = new ApiResponseAccount();

        if (updatedAccount != null) {
            response.setAccount(updatedAccount);
            response.setMessage("Account update completed");
            response.setStatusCode("200 OK");

        } else {
            response.setAccount(null);
            response.setMessage("Account update failed");
            response.setStatusCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
        }

        return response;
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponseAccount deleteAccount(@PathVariable("id") long accountId) {
        ApiResponseAccount response = new ApiResponseAccount();
        boolean deletionStatus = accountService.deleteAccount(accountId);

        if (deletionStatus) {
            response.setMessage("Account deleted successfully");
            response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
        } else {
            response.setMessage("Account not found or delete operation failed");
            response.setStatusCode(String.valueOf(HttpStatus.NOT_FOUND.value()));
        }

        return response;
    }

    @PostMapping("/registration/submit")
    public ResponseEntity<?> registrationAttempt(@RequestBody RegistrationRequest registrationRequest) {
        String username = registrationRequest.getUsername();
        String email = registrationRequest.getEmail();

        String encodedPassword = encoder.encode(registrationRequest.getPassword());

        if (encoder.matches(registrationRequest.getPassword(),encodedPassword)){
            RegistrationRequest regRequestWithEncodedPassword = new RegistrationRequest(registrationRequest.getUsername(), registrationRequest.getEmail(), encodedPassword);
            try {
                accountControllerLogger.info("Registration attempt | {}", registrationRequest.toString());
                Account account = accountService.registerUser(regRequestWithEncodedPassword);
                RegistrationResponse registrationResponse = new RegistrationResponse(account.getId(), account.getUsername(), account.getEmail());
                accountControllerLogger.info("Successful registration | JSON returned: {}", registrationResponse.toString());
                return ResponseEntity.status(HttpStatus.CREATED).body(registrationResponse);

            } catch (InvalidUsernameException e) {
                accountControllerLogger.error("Unsuccessful registration attempt due to invalid username. Username: {}", username);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid username entered.");

            } catch (InvalidEmailAddressException e) {
                accountControllerLogger.error("Unsuccessful registration attempt due to invalid email address. Email address: {}", email);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid email address entered.");

            } catch (InvalidPasswordException e) {
                accountControllerLogger.error("Unsuccessful registration attempt due to invalid password.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid password entered.");

            } catch (UnavailableEmailAddressException e) {
                accountControllerLogger.error("Unsuccessful registration attempt as email address has been registered to another user. Email address: {}", email);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email address entered is unavailable.");

            } catch (UnavailableUsernameException e) {
                accountControllerLogger.error("Unsuccessful registration attempt as username has been registered to another user. Username: {}", username);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username entered is unavailable.");

            } catch (Exception e) {
                accountControllerLogger.fatal("Unsuccessful registration attempt due to invalid an unexpected error.");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error occurred.");
            }
        }
        accountControllerLogger.fatal("Password and Encrypted passwords do not match. Please check password encryption.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong, please contact support team.");
    }

    @PostMapping("/login/submit")
    public ResponseEntity<?> loginAttempt(@RequestBody LoginRequest loginRequest) {
        try {
            accountControllerLogger.info("Login attempt | {}", loginRequest.toString());
            Account account = accountService.loginUser(loginRequest,encoder);
            LoginResponse loginResponse = new LoginResponse(account.getId(), account.getUsername());
            accountControllerLogger.info("Successful login | {}", loginResponse.toString());
            return ResponseEntity.status(HttpStatus.OK).body(loginResponse);

        } catch (UsernameNotFoundException e) {
            accountControllerLogger.error("Unsuccessful login as username entered not found. Username: {}", loginRequest.getUsername());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Username entered cannot be found.");

        } catch (IncorrectPasswordException e) {
            accountControllerLogger.error("Unsuccessful login due to incorrect password.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect password entered.");

        } catch (Exception e) {
            accountControllerLogger.fatal("Unsuccessful login attempt due to invalid an unexpected error.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error occurred.");
        }
    }

}
