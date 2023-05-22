package com.jorgebarrios.expensetracker.expense.exception;

public class UnknownExpenseException extends RuntimeException {
    public UnknownExpenseException(String id) {
        super("Unknown budget expense with id: " + id);
    }
}
