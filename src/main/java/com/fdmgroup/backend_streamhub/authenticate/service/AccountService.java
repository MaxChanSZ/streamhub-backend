package com.fdmgroup.backend_streamhub.authenticate.service;


import com.fdmgroup.backend_streamhub.authenticate.model.Account;
import com.fdmgroup.backend_streamhub.authenticate.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fdmgroup.backend_streamhub.authenticate.dto.LoginRequest;
import com.fdmgroup.backend_streamhub.authenticate.dto.RegistrationRequest;
import com.fdmgroup.backend_streamhub.authenticate.exceptions.*;
import com.fdmgroup.backend_streamhub.authenticate.model.Account;
import com.fdmgroup.backend_streamhub.authenticate.repository.AccountRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {

    private static final Logger accountServiceLogger = LogManager.getLogger(AccountService.class);

    @Autowired
    private AccountRepository accountRepository;


    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }


    public Account updateAccount(Account accountToUpdate) {
        return accountRepository.findById(accountToUpdate.getId())
                .map(existingAccount -> {
                    existingAccount.setUsername(accountToUpdate.getUsername());
                    existingAccount.setEmail(accountToUpdate.getEmail());
                    existingAccount.setPassword(accountToUpdate.getPassword());
                    return accountRepository.save(existingAccount);
                })
                .orElse(null); // Account not found
    }

    public boolean deleteAccount(long accountId) {
        return accountRepository.findById(accountId)
                .map(account -> {
                    accountRepository.delete(account);
                    return true; // Account deleted successfully
                })
                .orElse(false); // Account not found
    }

    public Account loginUser(LoginRequest loginRequest) throws  UsernameNotFoundException,
                                                                IncorrectPasswordException {
        accountServiceLogger.info("Login attempt | {}", loginRequest.toString());

        // Retrieve an account by username.
        Optional<Account> accountOptional = accountRepository.findByUsername(loginRequest.getUsername());

       // Unsuccessful login due to incorrect username.
       if (accountOptional.isEmpty()) {
           accountServiceLogger.error("Unsuccessful login as username entered not found.");
           throw new UsernameNotFoundException();
       }

       Account account = accountOptional.get();

       // Unsuccessful login due to incorrect password.

       if (!encoder.matches(loginRequest.getPassword(), account.getPassword())) {

           accountServiceLogger.error("Unsuccessful login due to incorrect password.");
           throw new IncorrectPasswordException();
       }
       accountServiceLogger.info("Successful login | {}", loginRequest.toString());
       return account;
    }

    public Account registerUser(RegistrationRequest registrationRequest) throws     InvalidUsernameException,
                                                                                    InvalidEmailAddressException,
                                                                                    InvalidPasswordException,
                                                                                    UnavailableUsernameException,
                                                                                    UnavailableEmailAddressException,
                                                                                    UnavailablePasswordException {
        accountServiceLogger.info("Registration attempt | {}", registrationRequest.toString());

        String username = registrationRequest.getUsername();
        String email = registrationRequest.getEmail();
        String password = registrationRequest.getPassword();

        if (!isValidUsername(username)) {
            throw new InvalidUsernameException();
        }

        if (!isValidEmailAddress(email)) {
            throw new InvalidEmailAddressException();
        }

        if (!isValidPassword(password)) {
            throw new InvalidPasswordException();
        }

        if (!isUsernameAvailable(username)) {
            throw new UnavailableUsernameException();
        }

        if (!isEmailAddressAvailable(email)) {
            throw new UnavailableEmailAddressException();
        }

        if (!isPasswordAvailable(password)) {
            throw new UnavailablePasswordException();
        }

        Account account = new Account(username, email, password);
        accountRepository.save(account);
        accountServiceLogger.info("Successful registration | {}", account.toString());
        return account;
    }

    private boolean isEmailAddressAvailable(String email) {
        return accountRepository.findByEmail(email).isEmpty();
    }

    private boolean isPasswordAvailable(String password) {
        return accountRepository.findByPassword(password).isEmpty();
    }

    private boolean isUsernameAvailable(String username) {
        return accountRepository.findByUsername(username).isEmpty();
    }

    private boolean isValidPassword(String password) {
        if (password.length() < 8) {
            return false;
        }

        int numberOfUppercaseLetters = 0;
        int numberOfNumbers = 0;
        int numberOfSpecialCharacters = 0;
        for (int i = 0; i < password.length(); i++) {
            char character = password.charAt(i);
            if (Character.isUpperCase(character)) {
                numberOfUppercaseLetters++;
            }
            if (Character.isDigit(character)) {
                numberOfNumbers++;
            }
            if (!Character.isLetterOrDigit(character)) {
                numberOfSpecialCharacters++;
            }
        }

        return numberOfUppercaseLetters >= 1 && numberOfNumbers >=1 && numberOfSpecialCharacters >= 1;
    }

    private boolean isValidEmailAddress(String email) {
        if (email == null) {
            return false;
        }
        String trimmedEmail = email.trim();
        // Regex pattern for basic email validation
        return trimmedEmail.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    }

    private boolean isValidUsername(String username) {
        String[] word = username.split(" ");
        if (word.length != 1) {
            return false;
        }

        int numberOfCharacters = 0;
        for (int i = 0; i < username.length(); i++) {
            numberOfCharacters++;
        }

        return numberOfCharacters >= 5;
    }
}
