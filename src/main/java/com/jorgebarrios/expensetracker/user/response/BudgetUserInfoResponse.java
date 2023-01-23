package com.jorgebarrios.expensetracker.user.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BudgetUserInfoResponse {
    private String userId;
    private String lastName;
    private String firstName;
    private String email;

    public BudgetUserInfoResponse(
            String userId,
            String lastName,
            String firstName,
            String email
                                 ) {
        this.userId = userId;
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
    }
}
