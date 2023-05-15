package com.jorgebarrios.expensetracker.expense.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryInfoDTO {
    private String name;
    private String color;
    private Double amountSpent;
    private Integer numOfTransaction;

}

