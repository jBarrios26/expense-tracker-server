package com.jorgebarrios.expensetracker.expense.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class DayExpenseDTO {
    private Integer weekDay;
    private Double spentTotal;

    public DayExpenseDTO(
            Object weekDay,
            Double spentTotal
    ) {
        this.spentTotal = spentTotal;
        if (weekDay instanceof Double) {
            this.weekDay = ((Double) weekDay).intValue();
        }
    }

    public static DayExpenseDTO empty(Integer day) {
        return new DayExpenseDTO(
                (double) day,
                0.0
        );
    }
}
