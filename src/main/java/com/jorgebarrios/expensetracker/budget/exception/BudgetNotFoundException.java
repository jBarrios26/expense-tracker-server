package com.jorgebarrios.expensetracker.budget.exception;

public class BudgetNotFoundException extends RuntimeException {
    public BudgetNotFoundException(String id) {
        super("Could not find budget with id: " + id);
    }
}
