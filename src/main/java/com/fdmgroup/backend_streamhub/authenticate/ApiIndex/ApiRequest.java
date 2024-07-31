package com.fdmgroup.backend_streamhub.authenticate.ApiIndex;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiRequest {
    private String message;
    private String statusCode;
}
