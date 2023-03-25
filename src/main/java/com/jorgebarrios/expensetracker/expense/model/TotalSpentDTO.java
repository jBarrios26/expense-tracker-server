package com.jorgebarrios.expensetracker.expense.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TotalSpentDTO {
    private Integer dateOfExpense;
    private Double totalSpent;

    public static  TotalSpentDTO empty(Integer day) {
        return new TotalSpentDTO(
                day,
                0.0
        );
    }
}
