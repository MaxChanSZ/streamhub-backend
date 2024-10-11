package com.fdmgroup.backend_streamhub.watchpartysession.service;

import com.fdmgroup.backend_streamhub.authenticate.model.Account;
import com.fdmgroup.backend_streamhub.authenticate.repository.AccountRepository;
import com.fdmgroup.backend_streamhub.videostream.service.VideoService;
import com.fdmgroup.backend_streamhub.watchpartysession.model.WatchParty;
import com.fdmgroup.backend_streamhub.watchpartysession.repository.IWatchPartyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WatchPartyService {

    @Autowired
    IWatchPartyRepository watchPartyRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    VideoService videoService;

    public WatchParty createWatchParty(String partyName, String password, Long accountID, String scheduledDate, String scheduledTime) {

        Optional<Account> account = accountRepository.findById(accountID);
        if (account.isPresent()) {
            WatchParty watchParty = new WatchParty();

            watchParty.setPartyName(partyName);
            watchParty.setAccount(account.get());
            watchParty.setPassword(password);

            watchParty.setScheduledDate(scheduledDate);
            watchParty.setScheduledTime(scheduledTime);

            // TODO change to find video by ID based on video selected by the user
            watchParty.setVideo(videoService.findVideoById(1).get());


            return watchPartyRepository.save(watchParty);
        } else {
            throw new RuntimeException("Account not found");
        }
    }

    public Optional<WatchParty> findByCode(String code) {
        return watchPartyRepository.findByCode(code);
    }

    public List<WatchParty> getAllWatchParties() {
        return watchPartyRepository.findAll();
    }

    public List<WatchParty> getWatchPartiesByUserId(Long userId) {
        Optional<Account> account = accountRepository.findById(userId);
        if (account.isPresent()) {
            return watchPartyRepository.findByAccount(account.get());
        } else {
            throw new RuntimeException("Account not found");
        }
    }

    public List<WatchParty> getAllWatchPartiesWithPoll() {
        return watchPartyRepository.findWatchPartiesWithPoll();
    }
}

