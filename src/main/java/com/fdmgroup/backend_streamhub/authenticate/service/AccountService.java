package com.fdmgroup.backend_streamhub.authenticate.service;

import com.fdmgroup.backend_streamhub.authenticate.dto.LoginRequest;
import com.fdmgroup.backend_streamhub.authenticate.exceptions.IncorrectPasswordException;
import com.fdmgroup.backend_streamhub.authenticate.exceptions.IncorrectUsernameOrEmailAddressException;
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

   public void loginUser(LoginRequest loginRequest) throws IncorrectUsernameOrEmailAddressException, IncorrectPasswordException {
        accountServiceLogger.info("Login attempt | {}.", loginRequest.toString());

        // Retrieve an account by username. If not found, retrieve by email address.
        Optional<Account> account = accountRepository.findByUsername(loginRequest.getUsernameOrEmail());
        if (account.isEmpty()) {
            account = accountRepository.findByEmail(loginRequest.getUsernameOrEmail());
        }

        // Unsuccessful login due to incorrect username or email address.
        if (account.isEmpty()) {
            accountServiceLogger.error("Unsuccessful login due to incorrect username or email address.");
            throw new IncorrectUsernameOrEmailAddressException();
        }

        // Unsuccessful login due to incorrect password.
        String accountPassword = account.get().getPassword();
        if (!accountPassword.equals(loginRequest.getPassword())) {
            accountServiceLogger.error("Unsuccessful login due to incorrect password.");
            throw new IncorrectPasswordException();
        }

        accountServiceLogger.info("Successful login | {}.", loginRequest.toString());
    }

}
