package com.jorgebarrios.expensetracker.user.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginResponse {
    private Boolean success;
    private String jwtToken;
    private String refreshToken;

    private String userId;

    public LoginResponse(
            Boolean success,
            String jwtToken,
            String refreshToken,
            String userId
                        ) {
        this.success = success;
        this.jwtToken = jwtToken;
        this.refreshToken = refreshToken;
        this.userId = userId;
    }

}
