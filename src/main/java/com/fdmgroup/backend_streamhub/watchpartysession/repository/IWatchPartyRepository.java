package com.fdmgroup.backend_streamhub.watchpartysession.repository;

import com.fdmgroup.backend_streamhub.authenticate.model.Account;
import com.fdmgroup.backend_streamhub.watchpartysession.model.WatchParty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IWatchPartyRepository extends JpaRepository<WatchParty, Long> {
    Optional<WatchParty> findByCode(String code);
    List<WatchParty> findByAccount(Account account);
    List<WatchParty> findByScheduledDateAndScheduledTimeBetweenAndReminderEmailSentFalse(
            String scheduledDate, String startTime, String endTime);
    @Query(value = "SELECT * FROM watch_party w WHERE w.id IN (SELECT p.watchparty_id FROM poll p)", nativeQuery = true)
    List<WatchParty> findWatchPartiesWithPoll();

    @Query(value = "SELECT * FROM watch_party w WHERE w.id NOT IN (SELECT p.watchparty_id FROM poll p)", nativeQuery = true)
    List<WatchParty> findWatchPartiesWithoutPoll();
}

