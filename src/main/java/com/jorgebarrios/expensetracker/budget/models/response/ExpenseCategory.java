package com.jorgebarrios.expensetracker.budget.models.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseCategory {
    private String name;
    private String color;
}
