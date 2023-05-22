package com.jorgebarrios.expensetracker.expense.exception;

import java.util.Date;

public class InvalidExpenseDateException extends RuntimeException{
    public InvalidExpenseDateException(Date date) {
        super("Date is not valid." + date.toString());
    }
}
