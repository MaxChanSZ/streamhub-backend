package com.fdmgroup.backend_streamhub.authenticate.ApiIndex;

import com.fdmgroup.backend_streamhub.authenticate.model.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiRequestAccount {
    // Currently not used, to change / adjust depending on implementation.
    private Account account;
}
