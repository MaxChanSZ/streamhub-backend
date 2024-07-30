/**
 * Repository interface for {@link com.fdmgroup.backend_streamhub.model.User} entities.
 */

package com.fdmgroup.backend_streamhub.repository;

import com.fdmgroup.backend_streamhub.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a User entity by username.
     *
     * @param username The username of the User entity.
     * @return an {@link Optional} containing the User entity if found, empty otherwise.
     */
    Optional<User> findByUsername(String username);


    /**
     * Finds a User entity by email address.
     *
     * @param email The email address of the User entity.
     * @return an {@link Optional} containing the User entity if found, empty otherwise.
     */
    Optional<User> findByEmailAddress(String email);

    /**
     * Finds a User entity by password.
     *
     * @param password The password of the User entity.
     * @return an {@link Optional} containing the User entity if found, empty otherwise.
     */
    Optional<User> findByPassword(String password);

}
