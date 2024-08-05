/**
 * Data transfer object for login operation.
 */

package com.fdmgroup.backend_streamhub.authenticate.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LoginRequest {

    /**
     * Username or email address entered during login attempt.
     */
    String usernameOrEmail;

    /**
     * Password entered during login attempt.
     */
    String password;

    @Override
    public String toString() {
        return "LoginRequest{" +
                "usernameOrEmail='" + usernameOrEmail + '\'' +
                '}';
    }

}
