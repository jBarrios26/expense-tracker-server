package com.jorgebarrios.expensetracker.expense.controller;

import com.jorgebarrios.expensetracker.budget.models.response.Expense;
import com.jorgebarrios.expensetracker.expense.BudgetExpense;
import com.jorgebarrios.expensetracker.expense.model.BudgetExpenseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/budget-expense")
@RequiredArgsConstructor
@RestController
public class BudgetExpenseController {

    @PutMapping("/{expenseId}")
    public ResponseEntity<Boolean> updateExpense(
            @PathVariable String expenseId,
            @RequestBody BudgetExpenseDTO expense
                                                ) {
        return ResponseEntity.ok(true);
    }
}
