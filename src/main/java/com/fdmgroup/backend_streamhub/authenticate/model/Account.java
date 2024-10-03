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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Account implements UserDetails {

    /**
     * The identification number of an Account entity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * The username of an Account entity.
     */
    private String username;

    /**
     * The password of an Account entity.
     */
    private String password;

    /**
     * The email address of an Account entity.
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
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    //FYI the methods below return true.
    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
