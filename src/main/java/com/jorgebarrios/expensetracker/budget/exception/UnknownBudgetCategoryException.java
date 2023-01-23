package com.jorgebarrios.expensetracker.budget.exception;

import java.util.List;
import java.util.UUID;

public class UnknownBudgetCategoryException extends RuntimeException {
    public UnknownBudgetCategoryException(List<UUID> unknownCategories) {
        super("Budget created with unknown categories: " +
              unknownCategories.stream()
                               .map(UUID::toString)
                               .reduce((category, acc) -> acc + " " +
                                                          category));
    }
}
