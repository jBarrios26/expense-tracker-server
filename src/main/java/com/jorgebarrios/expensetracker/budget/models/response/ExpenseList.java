package com.jorgebarrios.expensetracker.budget.models.response;

import com.jorgebarrios.expensetracker.common.model.Pagination;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseList {
    private List<Expense> expenseList;
    private Pagination pagination;
}
