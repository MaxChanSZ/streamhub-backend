/**
 * Service class that handles login-related operations.
 */

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
public class LoginService {

    /**
     * Logger to monitor operational flow and facilitate troubleshooting.
     */
    private static final Logger loginServiceLogger = LogManager.getLogger(LoginService.class);

    /**
     * Repository for account-related operations.
     */
    @Autowired
    private AccountRepository accountRepository;

    /**
     * Constructs a {@code LoginService} with the specified {@link AccountRepository}
     */
    public LoginService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    /**
     * Logs in the user.
     *
     * @param loginRequest The LoginRequest object containing user login data.
     * @throws IncorrectUsernameOrEmailAddressException The exception thrown for an unsuccessful login attempt due to invalid username or email address of password.
     */
    public void loginUser(LoginRequest loginRequest) throws IncorrectUsernameOrEmailAddressException, IncorrectPasswordException {
        loginServiceLogger.info("Login attempt | {}", loginRequest.toString());
        Optional<Account> account = accountRepository.findByUsername(loginRequest.getUsernameOrEmail());

        // Unsuccessful login due to incorrect username or email address.
        if (account.isEmpty()) {
            throw new IncorrectUsernameOrEmailAddressException();
        }

        // Unsuccessful login due to incorrect password.
        String accountPassword = account.get().getPassword();
        if (!accountPassword.equals(loginRequest.getPassword())) {
            throw new IncorrectPasswordException();
        }
    }

}
