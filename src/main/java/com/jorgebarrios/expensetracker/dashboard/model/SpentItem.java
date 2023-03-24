package com.jorgebarrios.expensetracker.dashboard.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class SpentItem {
    private String dateFormatted;
    private Double amount;

}
