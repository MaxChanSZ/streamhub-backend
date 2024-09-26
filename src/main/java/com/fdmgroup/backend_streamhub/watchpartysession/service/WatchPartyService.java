package com.fdmgroup.backend_streamhub.watchpartysession.service;

import com.fdmgroup.backend_streamhub.authenticate.model.Account;
import com.fdmgroup.backend_streamhub.authenticate.repository.AccountRepository;
import com.fdmgroup.backend_streamhub.videostream.repository.IVideoRepository;
import com.fdmgroup.backend_streamhub.watchpartysession.model.PollOption;
import com.fdmgroup.backend_streamhub.watchpartysession.repository.IPollOptionRepository;
import com.fdmgroup.backend_streamhub.watchpartysession.repository.IPollRepository;
import com.fdmgroup.backend_streamhub.watchpartysession.model.Poll;
import com.fdmgroup.backend_streamhub.watchpartysession.model.WatchParty;
import com.fdmgroup.backend_streamhub.watchpartysession.repository.IWatchPartyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WatchPartyService {

    @Autowired
    IWatchPartyRepository watchPartyRepository;
    @Autowired
    IVideoRepository videoRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    IPollRepository pollRepository;
    @Autowired
    IPollOptionRepository optionRepository;

    public WatchParty createWatchParty(String partyName, Long accountID) {
        Optional<Account> account = accountRepository.findById(accountID);
        if (account.isPresent()) {
            WatchParty watchParty = new WatchParty();

            watchParty.setPartyName(partyName);
            watchParty.setAccount(account.get());

            return watchPartyRepository.save(watchParty);
        } else {
            throw new RuntimeException("Account not found");
        }
    }
    public Optional<WatchParty> findByCode(String code) {
        return watchPartyRepository.findByCode(code);
    }

    public Poll createPoll(Long watchPartyId, String question) {
        Optional<WatchParty> watchParty = watchPartyRepository.findById(watchPartyId);

        if (watchParty.isPresent()) {
            Poll poll = new Poll();

            poll.setQuestion(question);
            poll.setWatchParty(watchParty.get());

            return pollRepository.save(poll);
        } else {
            throw new RuntimeException("Watchparty not found");
        }
    }

    public PollOption createOption(Long pollId, String value) {
        Optional<Poll> poll = pollRepository.findById(pollId);

        if (poll.isPresent()) {
            PollOption option = new PollOption();
            option.setValue(value);
            option.setPoll(poll.get());

            return optionRepository.save(option);
        } else {
            throw new RuntimeException("Poll not found");
        }
    }
}
