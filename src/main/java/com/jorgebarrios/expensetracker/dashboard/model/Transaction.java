package com.jorgebarrios.expensetracker.dashboard.model;

import lombok.*;

import java.util.Date;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    private String color;
    private String name;
    private Double amountSpent;
    private Date date;
}
