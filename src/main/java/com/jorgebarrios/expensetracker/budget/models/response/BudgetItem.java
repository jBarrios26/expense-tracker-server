package com.jorgebarrios.expensetracker.budget.models.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BudgetItem {
    String budgetId;
    String name;
    String description;
    Double budgetLimit;
    Double totalSpending;
    Date creationDate;
    List<BudgetItemCategories> categories;

}
