package com.jorgebarrios.expensetracker.budget.models.response;

import com.jorgebarrios.expensetracker.common.model.Pagination;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@AllArgsConstructor
@Setter
public class CurrentMonthBudgetListResponse {
    private Pagination pagination;
    private List<CurrentMonthBudget> budgets;
}


