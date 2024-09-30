package com.fdmgroup.backend_streamhub.watchpartysession.service;

import com.fdmgroup.backend_streamhub.watchpartysession.model.Poll;
import com.fdmgroup.backend_streamhub.watchpartysession.model.PollOption;
import com.fdmgroup.backend_streamhub.watchpartysession.model.WatchParty;
import com.fdmgroup.backend_streamhub.watchpartysession.repository.IPollOptionRepository;
import com.fdmgroup.backend_streamhub.watchpartysession.repository.IPollRepository;
import com.fdmgroup.backend_streamhub.watchpartysession.repository.IWatchPartyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PollService {
    @Autowired
    IWatchPartyRepository watchPartyRepository;
    @Autowired
    IPollRepository pollRepository;
    @Autowired
    IPollOptionRepository pollOptionRepository;

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

    public PollOption createOption(Long pollId, String value, String description, String fileName) {
        Optional<Poll> poll = pollRepository.findById(pollId);

        if (poll.isPresent()) {
            PollOption option = new PollOption();
            option.setValue(value);
            option.setPoll(poll.get());
            option.setDescription(description);
            PollOption newOption = pollOptionRepository.save(option);
            String newImageUrl = newOption.getPoll().getId() + "-" + newOption.getId() + "-" + fileName;
            newOption.setImageUrl(newImageUrl);
            return pollOptionRepository.save(newOption);
        } else {
            throw new RuntimeException("Poll not found");
        }
    }

    public List<PollOption> getPollOptionsByPoll(Long pollId) {
        return pollOptionRepository.findByPollId(pollId);
    }
}
