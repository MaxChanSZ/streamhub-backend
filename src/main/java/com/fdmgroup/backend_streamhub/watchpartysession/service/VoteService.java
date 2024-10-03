package com.fdmgroup.backend_streamhub.watchpartysession.service;

import com.fdmgroup.backend_streamhub.authenticate.model.Account;
import com.fdmgroup.backend_streamhub.authenticate.repository.AccountRepository;
import com.fdmgroup.backend_streamhub.watchpartysession.model.Poll;
import com.fdmgroup.backend_streamhub.watchpartysession.model.PollOption;
import com.fdmgroup.backend_streamhub.watchpartysession.model.Vote;
import com.fdmgroup.backend_streamhub.watchpartysession.repository.IPollOptionRepository;
import com.fdmgroup.backend_streamhub.watchpartysession.repository.IPollRepository;
import com.fdmgroup.backend_streamhub.watchpartysession.repository.IVoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VoteService {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    IPollRepository pollRepository;
    @Autowired
    IPollOptionRepository pollOptionRepository;
    @Autowired
    IVoteRepository voteRepository;

    public Vote createVote(Long pollId, Long pollOptionId, Long accountId) {
        Optional<Poll> poll = pollRepository.findById(pollId);
        Optional<PollOption> pollOption = pollOptionRepository.findById(pollOptionId);
        Optional<Account> account = accountRepository.findById(accountId);

        // check if the poll, poll option and account is available
        if (poll.isEmpty()) {
            throw new RuntimeException("Poll not found");
        } if (pollOption.isEmpty()) {
            throw new RuntimeException("Poll Option not found");
        } if (account.isEmpty()) {
            throw new RuntimeException("Account not found");
        }

        //check if user has casted vote on this poll previously
        Optional<Vote> prevVote = voteRepository.findByPollAndAccount(poll.get(), account.get());

        if (prevVote.isPresent()) {
            throw new RuntimeException("User already voted on this poll");
        }

        Vote vote = new Vote();
        vote.setPoll(poll.get());
        vote.setPollOption(pollOption.get());
        vote.setAccount(account.get());

        return voteRepository.save(vote);
    }

    public Vote changeVote(long pollId, long newPollOptionId, long accountId) {
        // find present vote of the user on the specific poll
        Optional<Poll> poll = pollRepository.findById(pollId);
        Optional<Account> account = accountRepository.findById(accountId);
        if (poll.isEmpty()) {
            throw new RuntimeException("Poll not found");
        } if (account.isEmpty()) {
            throw new RuntimeException("Account not found");
        }
        Optional<Vote> prevVote = voteRepository.findByPollAndAccount(poll.get(), account.get());
        if (prevVote.isPresent()) {
            Vote newVote = prevVote.get();
            Optional<PollOption> pollOption = pollOptionRepository.findByIdAndPollId(newPollOptionId, pollId);
            if (pollOption.isPresent()) {
                // update new poll option if it exists
                newVote.setPollOption(pollOption.get());
                return voteRepository.save(newVote);
            } else {
                throw new RuntimeException("Poll Option does not exist in this poll");
            }

        } else {
            throw new RuntimeException("Vote not yet done by user in this poll");
        }
    }
}
