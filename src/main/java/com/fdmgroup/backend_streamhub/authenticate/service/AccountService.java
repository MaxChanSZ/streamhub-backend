package com.fdmgroup.backend_streamhub.authenticate.service;

import com.fdmgroup.backend_streamhub.authenticate.model.Account;
import com.fdmgroup.backend_streamhub.authenticate.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
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


}
