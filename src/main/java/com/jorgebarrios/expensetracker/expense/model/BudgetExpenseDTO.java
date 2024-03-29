package com.jorgebarrios.expensetracker.expense.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class BudgetExpenseDTO {
    private String name;
    private Date expenseDate;
    private Double amount;
}
