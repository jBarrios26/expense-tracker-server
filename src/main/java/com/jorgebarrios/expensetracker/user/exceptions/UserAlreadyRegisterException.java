package com.jorgebarrios.expensetracker.user.exceptions;

public class UserAlreadyRegisterException extends  RuntimeException{
    public UserAlreadyRegisterException(String email) {
        super("User with email " + email + " is already registered.");
    }
}
