package com.jorgebarrios.expensetracker.dashboard.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class DayExpense {
    Integer day;
    Double totalSpent;
}
