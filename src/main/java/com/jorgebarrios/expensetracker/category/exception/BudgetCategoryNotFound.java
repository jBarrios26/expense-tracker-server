package com.jorgebarrios.expensetracker.category.exception;

public class BudgetCategoryNotFound extends RuntimeException {
    public BudgetCategoryNotFound(String id) {
        super("Category with id " + id + " not found");
    }
}
