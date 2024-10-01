package com.fdmgroup.backend_streamhub.watchpartysession.repository;

import com.fdmgroup.backend_streamhub.watchpartysession.model.WatchParty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WatchPartyRepository extends JpaRepository<WatchParty, Long> {
    Optional<WatchParty> findByCode(String code);
}
