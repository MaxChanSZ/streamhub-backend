package com.fdmgroup.backend_streamhub.watchpartysession.repository;

import com.fdmgroup.backend_streamhub.watchpartysession.model.Poll;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IPollRepository extends JpaRepository<Poll, Long> {
    Optional<Poll> findByWatchPartyId(long watchPartyId);
}
