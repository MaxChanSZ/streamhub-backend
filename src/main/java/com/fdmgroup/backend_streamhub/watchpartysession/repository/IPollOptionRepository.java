package com.fdmgroup.backend_streamhub.watchpartysession.repository;

import com.fdmgroup.backend_streamhub.watchpartysession.model.PollOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IPollOptionRepository extends JpaRepository<PollOption, Long> {
    Optional<PollOption> findById(long pollOptionId);
}
