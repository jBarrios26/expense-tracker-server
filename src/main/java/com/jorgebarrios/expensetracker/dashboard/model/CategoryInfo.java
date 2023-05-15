package com.jorgebarrios.expensetracker.dashboard.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryInfo {
    private String name;
    private String color;
    private Double amountSpent;

}
