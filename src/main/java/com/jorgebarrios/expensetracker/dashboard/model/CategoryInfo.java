package com.jorgebarrios.expensetracker.dashboard.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryInfo {
    private String color;
    private String name;
    private String amountSpent;

}
