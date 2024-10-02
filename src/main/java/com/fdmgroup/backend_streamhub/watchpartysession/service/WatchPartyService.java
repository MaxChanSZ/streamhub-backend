package com.fdmgroup.backend_streamhub.watchpartysession.service;

import com.fdmgroup.backend_streamhub.authenticate.model.Account;
import com.fdmgroup.backend_streamhub.authenticate.repository.AccountRepository;
import com.fdmgroup.backend_streamhub.watchpartysession.exceptions.WatchPartyNotFoundException;
import com.fdmgroup.backend_streamhub.watchpartysession.model.WatchParty;
import com.fdmgroup.backend_streamhub.watchpartysession.repository.IWatchPartyRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WatchPartyService {

    @Autowired
    IWatchPartyRepository watchPartyRepository;
    @Autowired
    AccountRepository accountRepository;

    private static final Logger logger = LogManager.getLogger(WatchPartyService.class);

    public void addParticipantToWatchParty(String username, String code) {
        try {
            logger.info("Adding participant to WatchParty (Code: {}): {}", code, username);
            Optional<WatchParty> watchPartyOptional = findByCode(code);
            if (watchPartyOptional.isPresent()) {
                WatchParty watchParty = watchPartyOptional.get();
                watchParty.addParticipant(username);
            } else {
                throw new WatchPartyNotFoundException();
            }
        } catch (WatchPartyNotFoundException e) {
            logger.error("Watch Party with code {} cannot be found", code);

        } catch (Exception e) {
            logger.fatal("Unable to add participant to list of participants in WatchParty due to an unexpected error");
        }
    }

    public WatchParty createWatchParty(String partyName, Long accountID, String scheduledDate, String scheduledTime) {
        Optional<Account> account = accountRepository.findById(accountID);
        if (account.isPresent()) {
            WatchParty watchParty = new WatchParty();
            watchParty.setPartyName(partyName);
            watchParty.setAccount(account.get());
            watchParty.setScheduledDate(scheduledDate);
            watchParty.setScheduledTime(scheduledTime);
            return watchPartyRepository.save(watchParty);
        } else {
            throw new RuntimeException("Account not found");
        }
    }

    public Optional<WatchParty> findByCode(String code) {
        return watchPartyRepository.findByCode(code);
    }

}
