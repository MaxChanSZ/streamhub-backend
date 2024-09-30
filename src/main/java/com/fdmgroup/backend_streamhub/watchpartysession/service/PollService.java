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
            // save description when not empty
            if (!description.isEmpty()) {
                option.setDescription(description);
            }
            PollOption newOption = pollOptionRepository.save(option);
            if (fileName == null) {
               return newOption;
            } else {
                // save image url in the db if there is any images
                String newImageUrl = newOption.getPoll().getId() + "-" + newOption.getId() + "-" + fileName;
                newOption.setImageUrl(newImageUrl);
                return pollOptionRepository.save(newOption);
            }

        } else {
            throw new RuntimeException("Poll not found");
        }
    }

    public List<PollOption> getPollOptionsByPoll(Long pollId) {
        return pollOptionRepository.findByPollId(pollId);
    }

    public Poll getPollByWatchPartyId(Long watchPartyId) {
        Optional<Poll> poll =  pollRepository.findByWatchPartyId(watchPartyId);
        return poll.orElse(null);
    }

    public PollOption getPollOption(Long pollOptionId) {
        Optional<PollOption> pollOption = pollOptionRepository.findById(pollOptionId);
        return pollOption.orElse(null);
    }
}
