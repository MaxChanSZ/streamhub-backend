package com.fdmgroup.backend_streamhub.watchpartysession.repository;

import com.fdmgroup.backend_streamhub.authenticate.model.Account;
import com.fdmgroup.backend_streamhub.watchpartysession.model.Poll;
import com.fdmgroup.backend_streamhub.watchpartysession.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IVoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findByPollAndAccount(Poll poll, Account account);

    @Query(value = "SELECT po.id, COUNT(v.poll_option_id) AS vote_count FROM vote v RIGHT JOIN poll_option po ON v.poll_option_id=po.id WHERE po.poll_id= ?1 GROUP BY po.id", nativeQuery = true)
    List<Map<Long, Long>> getVoteCountByPollId(long pollId);
}
