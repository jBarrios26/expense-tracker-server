package com.jorgebarrios.expensetracker.budget.models.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Expense {
    private UUID id;
    private String name;
    private Double amount;
    private Date dateOfExpense;
    private ExpenseCategory category;
}
