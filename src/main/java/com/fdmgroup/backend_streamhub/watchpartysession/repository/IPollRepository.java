package com.fdmgroup.backend_streamhub.watchpartysession.repository;

import com.fdmgroup.backend_streamhub.watchpartysession.model.Poll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface IPollRepository extends JpaRepository<Poll, Long> {
    Optional<Poll> findByWatchPartyId(long watchPartyId);

    @Query(value = "SELECT * FROM poll p WHERE p.watchparty_id IN (SELECT w.id FROM watch_party w WHERE w.code = ?1)", nativeQuery = true)
    Optional<Poll> getPollIdByWatchPartyCode(String code);
}
