package com.fdmgroup.backend_streamhub.authenticate.service;

import com.fdmgroup.backend_streamhub.authenticate.dto.LoginRequest;
import com.fdmgroup.backend_streamhub.authenticate.exceptions.IncorrectPasswordException;
import com.fdmgroup.backend_streamhub.authenticate.exceptions.UsernameNotFoundException;
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

   public Account loginUser(LoginRequest loginRequest) throws   UsernameNotFoundException,
                                                                IncorrectPasswordException {
        accountServiceLogger.info("Login attempt | {}.", loginRequest.toString());

        // Retrieve an account by username.
        Optional<Account> accountOptional = accountRepository.findByUsername(loginRequest.getUsername());

       // Unsuccessful login due to incorrect username.
       if (accountOptional.isEmpty()) {
           accountServiceLogger.error("Unsuccessful login as username entered not found.");
           throw new UsernameNotFoundException();
       }

        Account account = accountOptional.get();

        // Unsuccessful login due to incorrect password.
        if (!account.getPassword().equals(loginRequest.getPassword())) {
            accountServiceLogger.error("Unsuccessful login due to incorrect password.");
            throw new IncorrectPasswordException();
        }
        accountServiceLogger.info("Successful login | {}.", loginRequest.toString());
        return account;
    }

}
