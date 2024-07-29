/**
 * Representation of a User entity in the application.
 * This class is mapped to the "users" table in the database.
 */

package com.fdmgroup.backend_streamhub.model;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "users")
public class User {

    /**
     * Identification number of a User entity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * Username of a User entity.
     */
    @Getter
    @Column(unique = true)
    private String username;

    /**
     * Email address of a User entity.
     */
    @Getter
    @Column(unique = true)
    private String email;

    /**
     * Password of a User entity.
     */
    @Getter
    @Column(unique = true)
    private String password;

    /**
     * Default constructor as required by Hibernate.
     */
    public User() {

    }

    /**
     * Constructor for a User entity.
     *
     * @param username  The username of a User entity.
     * @param email     The email address of a User entity.
     * @param password  The password of a User entity.
     */
    public User(String username, String email, String password) {
        this.email = email;
        this.password = password;
        this.username = username;
    }

}
