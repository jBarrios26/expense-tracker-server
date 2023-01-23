package com.jorgebarrios.expensetracker.budget.models.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateBudgetResponse {

    private String budgetId;
    private String budgetName;
    private boolean success;

    public CreateBudgetResponse(
            String budgetName,
            String budgetId,
            boolean success
                               ) {
        this.budgetId = budgetId;
        this.budgetName = budgetName;
        this.success = success;
    }
}
