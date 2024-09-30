package com.fdmgroup.backend_streamhub.watchpartysession.repository;

import com.fdmgroup.backend_streamhub.watchpartysession.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IVoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findByPollIdAndUserId(long pollId, long userId);
}
