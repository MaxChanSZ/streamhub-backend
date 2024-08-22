package com.fdmgroup.backend_streamhub.watchpartysession.repository;

import com.fdmgroup.backend_streamhub.watchpartysession.model.WatchParty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IWatchPartyRepository extends JpaRepository<WatchParty, Long> {
}
