package com.fdmgroup.backend_streamhub.authenticate.dto;

import com.fdmgroup.backend_streamhub.authenticate.model.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RegistrationResponse {

    private long id;
    private String username;
    private String email;

    @Override
    public String toString() {
        return "RegistrationResponse{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
