package com.jorgebarrios.expensetracker.dashboard.model.response;

import com.jorgebarrios.expensetracker.dashboard.model.SpentItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ExpenseChartList
{
    private List<SpentItem> expenses;
}
