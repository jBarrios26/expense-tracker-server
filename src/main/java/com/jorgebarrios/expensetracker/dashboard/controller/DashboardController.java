package com.jorgebarrios.expensetracker.dashboard.controller;

import com.jorgebarrios.expensetracker.dashboard.model.CategoryInfo;
import com.jorgebarrios.expensetracker.dashboard.model.SpentItem;
import com.jorgebarrios.expensetracker.dashboard.model.Transaction;
import com.jorgebarrios.expensetracker.dashboard.model.response.ExpenseChartList;
import com.jorgebarrios.expensetracker.dashboard.model.response.LastTransationResponse;
import com.jorgebarrios.expensetracker.dashboard.model.response.TopCategoriesResponse;
import com.jorgebarrios.expensetracker.dashboard.service.DashboardService;
import com.jorgebarrios.expensetracker.expense.BudgetExpense;
import com.jorgebarrios.expensetracker.expense.model.TotalSpentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("api/dashboard")
@RequiredArgsConstructor
@RestController
public class DashboardController {
    private final DashboardService dashboardService;

    @GetMapping("/last-transactions/{userId}")
    public ResponseEntity<LastTransationResponse> getLastTransactions(
            @PathVariable(required = true)
            String userId
    ) {
        List<BudgetExpense> budgetExpenseList =
                dashboardService.getLastTransactions(UUID.fromString(userId));
        return ResponseEntity.ok(
                new LastTransationResponse(budgetExpenseList.stream()
                                                            .map(budgetExpense -> new Transaction(
                                                                         budgetExpense.getBudgetCategory()
                                                                                      .getColor(),
                                                                         budgetExpense.getName(),
                                                                         budgetExpense.getAmount(),
                                                                         budgetExpense.getExpenseDate()
                                                                 )
                                                            )
                                                            .toList()
                )
        );

    }

    @GetMapping("/total-spent/{userId}")
    public ResponseEntity<ExpenseChartList> getTotalSpent(
            @PathVariable
            String userId,
            @RequestParam(
                    name = "year",
                    defaultValue = "2022"
            )
            int year
    ) {
        final List<TotalSpentDTO> expenseByMonth =
                dashboardService.getTotalSpentByMonth(
                        UUID.fromString(userId),
                        year
                );
        return ResponseEntity.ok(new ExpenseChartList(expenseByMonth.stream()
                                                                    .map(expense -> new SpentItem(
                                                                                 expense.getDateOfExpense()
                                                                                        .toString(),
                                                                                 expense.getTotalSpent()
                                                                         )
                                                                    )
                                                                    .toList()
                                 )
        );
    }

    @GetMapping("/total-spent-by-month/{userId}")
    public ResponseEntity<ExpenseChartList> getTotalSpentByMonth(
            @PathVariable
            String userId,
            @RequestParam(
                    name = "month",
                    defaultValue = "1"
            )
            int month,
            @RequestParam(
                    name = "year",
                    defaultValue = "2023"
            )
            int year
    ) {
        final List<SpentItem> expenseByMonth =
                dashboardService.getTotalSpentByDay(
                        UUID.fromString(userId),
                        month,
                        year
                );
        return ResponseEntity.ok(new ExpenseChartList(expenseByMonth
                                 )
        );
    }


    @GetMapping("/categories/{userId}")
    public ResponseEntity<TopCategoriesResponse> getTopCategories(
            @PathVariable
            String userId
    ) {
        List<CategoryInfo> topCategories =
                dashboardService.getTopCategories(userId);
        return ResponseEntity.ok(new TopCategoriesResponse(topCategories));
    }

}
