package com.jorgebarrios.expensetracker.common.exception;

public class BudgetUserNotFoundException extends RuntimeException {
    public BudgetUserNotFoundException(final String id) {
        super("User with " + id + " does not exists");
    }
}
