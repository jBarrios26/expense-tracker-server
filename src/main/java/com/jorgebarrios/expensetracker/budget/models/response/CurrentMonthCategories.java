package com.jorgebarrios.expensetracker.budget.models.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CurrentMonthCategories {
    private String color;
    private String name;
}
