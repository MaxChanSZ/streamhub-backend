/**
 * Repository interface for {@link com.fdmgroup.backend_streamhub.authenticate.model.Account} entities.
 */

package com.fdmgroup.backend_streamhub.authenticate.repository;

import com.fdmgroup.backend_streamhub.authenticate.model.Account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    /**
     * Finds an Account entity by username.
     *
     * @param username The username of the Account entity.
     * @return an {@link Optional} containing the User entity if found, empty otherwise.
     */
    Optional<Account> findByUsername(String username);

    /**
     * Finds an Account entity by email address.
     *
     * @param email The email address of the Account entity.
     * @return an {@link Optional} containing the Account entity if found, empty otherwise.
     */
    Optional<Account> findByEmail(String email);

    /**
     * Finds an Account entity by password.
     *
     * @param password The password of the Account entity.
     * @return an {@link Optional} containing the Account entity if found, empty otherwise.
     */
    Optional<Account> findByPassword(String password);

}
