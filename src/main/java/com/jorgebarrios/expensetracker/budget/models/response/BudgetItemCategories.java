package com.jorgebarrios.expensetracker.budget.models.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BudgetItemCategories {
    private UUID id;
    private String name;
    private String color;
    private Double limit;
    private Double currentSpending;
}
