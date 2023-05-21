package com.jorgebarrios.expensetracker.expense.controller;

import com.jorgebarrios.expensetracker.expense.BudgetExpense;
import com.jorgebarrios.expensetracker.expense.model.BudgetExpenseDTO;
import com.jorgebarrios.expensetracker.expense.service.BudgetExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/budget-expense")
@RequiredArgsConstructor
@RestController
public class BudgetExpenseController {

    private final BudgetExpenseService budgetExpenseService;

    @PutMapping("/{expenseId}")
    public ResponseEntity<BudgetExpense> updateExpense(
            @PathVariable String expenseId,
            @RequestBody BudgetExpenseDTO expense
    ) {
        BudgetExpense updatedExpense =
                budgetExpenseService.editExpense(
                        expenseId,
                        expense
                );
        return ResponseEntity.ok(updatedExpense);
    }

    @DeleteMapping("/{expenseId}")
    public ResponseEntity<Boolean> removeExpense(
            @PathVariable String expenseId
    ) {
        boolean wasSuccessfullyDeleted =
                budgetExpenseService.removeExpense(
                        expenseId
                );
        return ResponseEntity.ok(wasSuccessfullyDeleted);
    }
}
