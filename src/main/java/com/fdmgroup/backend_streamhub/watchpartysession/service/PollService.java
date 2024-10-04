package com.fdmgroup.backend_streamhub.watchpartysession.service;

import com.fdmgroup.backend_streamhub.authenticate.model.Account;
import com.fdmgroup.backend_streamhub.authenticate.repository.AccountRepository;
import com.fdmgroup.backend_streamhub.watchpartysession.dto.PollOptionResponse;
import com.fdmgroup.backend_streamhub.watchpartysession.dto.WatchPartyPollResponse;
import com.fdmgroup.backend_streamhub.watchpartysession.model.Poll;
import com.fdmgroup.backend_streamhub.watchpartysession.model.PollOption;
import com.fdmgroup.backend_streamhub.watchpartysession.model.Vote;
import com.fdmgroup.backend_streamhub.watchpartysession.model.WatchParty;
import com.fdmgroup.backend_streamhub.watchpartysession.repository.IPollOptionRepository;
import com.fdmgroup.backend_streamhub.watchpartysession.repository.IPollRepository;
import com.fdmgroup.backend_streamhub.watchpartysession.repository.IVoteRepository;
import com.fdmgroup.backend_streamhub.watchpartysession.repository.IWatchPartyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PollService {
    @Autowired
    IWatchPartyRepository watchPartyRepository;
    @Autowired
    IPollRepository pollRepository;
    @Autowired
    IPollOptionRepository pollOptionRepository;
    @Autowired
    IVoteRepository voteRepository;
    @Autowired
    AccountRepository accountRepository;

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

    public WatchPartyPollResponse getWatchPartyPollResponse(String code, long userId) {
        Optional<Account> account = accountRepository.findById(userId);
        WatchPartyPollResponse response = new WatchPartyPollResponse();
        Vote prevVote = new Vote();

        //query 1: return poll id of the watch party based on code?
        Optional<Poll> watchPartyPoll = pollRepository.getPollIdByWatchPartyCode(code);
        if(watchPartyPoll.isPresent() && account.isPresent()) {
            //check if user has casted vote on this poll previously
            Optional<Vote> prevVoteOptional = voteRepository.findByPollAndAccount(watchPartyPoll.get(), account.get());
            if (prevVoteOptional.isPresent()){
                prevVote = prevVoteOptional.get();
                response.setVoted(true);
            } else {
                prevVote = null;
            }
            response.setPollId(watchPartyPoll.get().getId());
            response.setPollQuestion(watchPartyPoll.get().getQuestion());

        } else {
            prevVote = null;
            if(account.isEmpty()) {
                throw new RuntimeException("User not logged in!");
            } else {
                throw new RuntimeException("No poll created for this watch party");
            }
        }

        //query 2: return list of poll options based on poll id
        List<PollOption> pollOptions = pollOptionRepository.findByPollId(watchPartyPoll.get().getId());
        if(pollOptions.size() >= 2) {
            List<PollOptionResponse> pollOptionResponses = new ArrayList<>();
            Vote finalPrevVote = prevVote;

            pollOptionResponses = pollOptions.stream().map(pollOption -> {
                        PollOptionResponse optionResponse = new PollOptionResponse();
                        optionResponse.setPollOptionId(pollOption.getId());
                        optionResponse.setValue(pollOption.getValue());
                        optionResponse.setDescription(pollOption.getDescription());
                        optionResponse.setImageUrl(pollOption.getImageUrl());
                        if(finalPrevVote != null && (finalPrevVote.getPollOption().getId() == pollOption.getId())) {
                            response.setSelectedPollOption(optionResponse);
                        }
                        return optionResponse;
                    }).collect(Collectors.toList());

            //query 3: return vote count for each poll option of the poll id
            List<Map<Long, Long>> voteCountList = voteRepository.getVoteCountByPollId(watchPartyPoll.get().getId());
            for(int i=0; i<voteCountList.size(); i++){
                Map<Long,Long> voteCount = voteCountList.get(i);
                PollOptionResponse optionResponseWithVote = pollOptionResponses.get(i);
                optionResponseWithVote.setVoteCount(voteCount.get("vote_count"));
            }

            response.setPollOptionList(pollOptionResponses);
        } else {
            throw new RuntimeException("No poll option created for this poll");
        }

        return response;
    }
}
