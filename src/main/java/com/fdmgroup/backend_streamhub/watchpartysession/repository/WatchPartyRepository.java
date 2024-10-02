package com.fdmgroup.backend_streamhub.watchpartysession.repository;

import com.fdmgroup.backend_streamhub.watchpartysession.model.WatchParty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WatchPartyRepository extends JpaRepository<WatchParty, Long> {
    List<WatchParty> findByScheduledDateAndScheduledTimeBetween(String scheduledDate, String startTime, String endTime);
}


