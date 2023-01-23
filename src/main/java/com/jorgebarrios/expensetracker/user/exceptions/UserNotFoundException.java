package com.jorgebarrios.expensetracker.user.exceptions;

public class UserNotFoundException extends  RuntimeException{
    public  UserNotFoundException(final String email) {
        super("User with email " + email + " doesnt exists");
    }
}
