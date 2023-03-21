package com.jorgebarrios.expensetracker.budget.models.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@AllArgsConstructor
@Setter
public class MonthBudget {
    private String budgetId;
    private String name;
    private Date createdAt;
    private List<MonthCategories> topCategories;

}

