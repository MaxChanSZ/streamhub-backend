package com.fdmgroup.backend_streamhub.watchpartysession.repository;

import com.fdmgroup.backend_streamhub.authenticate.model.Account;
import com.fdmgroup.backend_streamhub.watchpartysession.model.Poll;
import com.fdmgroup.backend_streamhub.watchpartysession.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IVoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findByPollAndAccount(Poll poll, Account account);
}
