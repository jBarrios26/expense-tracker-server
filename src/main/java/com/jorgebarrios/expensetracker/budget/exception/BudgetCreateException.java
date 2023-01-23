package com.jorgebarrios.expensetracker.budget.exception;

public class BudgetCreateException extends RuntimeException {
    public BudgetCreateException() {
        super("Could not create new budget");
    }
}
