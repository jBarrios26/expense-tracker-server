package com.jorgebarrios.expensetracker.dashboard.model.response;

import com.jorgebarrios.expensetracker.dashboard.model.Transaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LastTransationResponse {
    private List<Transaction> lastTransactions;
}
