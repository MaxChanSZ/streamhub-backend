package com.fdmgroup.backend_streamhub.watchpartysession.repository;

import com.fdmgroup.backend_streamhub.authenticate.model.Account;
import com.fdmgroup.backend_streamhub.watchpartysession.model.WatchParty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IWatchPartyRepository extends JpaRepository<WatchParty, Long> {
    Optional<WatchParty> findByCode(String code);
    List<WatchParty> findByAccount(Account account);
}

