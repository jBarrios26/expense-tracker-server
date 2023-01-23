package com.jorgebarrios.expensetracker.user.response;

public class SignUpResponse {
    private  Boolean success;

    public SignUpResponse(Boolean success) {
        this.success = success;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
