/**
 * Entity class for a registered user.
 */

package com.fdmgroup.backend_streamhub.authenticate.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Account {

    /**
     * The identification number of an account.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    /**
     * The username of an account.
     */
    private String username;

    /**
     * The password of an account.
     */
    private String password;

    /**
     * The email address of an account.
     */
    private String email;

    /**
     * Constructor for an Account entity.
     *
     * @param username  The username of an Account entity.
     * @param email     The email address of an Account entity.
     * @param password  The password of an Account entity.
     */
    public Account(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    /**
     * Returns a String representation of the {@code Account} entity.
     *
     * @return a String representation of the {@code Account} entity.
     */
    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

}
