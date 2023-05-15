package com.jorgebarrios.expensetracker.dashboard.model.response;

import com.jorgebarrios.expensetracker.expense.model.DayExpenseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SpentByDayResponse {
    List<DayExpenseDTO> expenseInWeekList;
}
