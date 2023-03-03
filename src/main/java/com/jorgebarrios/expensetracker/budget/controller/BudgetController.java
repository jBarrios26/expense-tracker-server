package com.jorgebarrios.expensetracker.budget.controller;

import com.jorgebarrios.expensetracker.budget.Budget;
import com.jorgebarrios.expensetracker.budget.exception.BudgetCreateException;
import com.jorgebarrios.expensetracker.budget.models.dto.CreateBudgetDTO;
import com.jorgebarrios.expensetracker.budget.models.dto.CreateExpenseDTO;
import com.jorgebarrios.expensetracker.budget.models.response.*;
import com.jorgebarrios.expensetracker.budget.service.BudgetService;
import com.jorgebarrios.expensetracker.common.model.Pagination;
import com.jorgebarrios.expensetracker.expense.BudgetExpense;
import com.jorgebarrios.expensetracker.user.BudgetUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.UUID;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/budget")
@RequiredArgsConstructor
@RestController
public class BudgetController {

    private final BudgetService budgetService;
    private final BudgetUserService budgetUserService;

    @PostMapping("/create-budget")
    @Operation(summary = "Create new budget for associated user", security = @SecurityRequirement(name = "Auth JWT"), method = "POST", responses = {
            @ApiResponse(responseCode = "201", description = "Create " +
                                                             "budget " +
                                                             "Successfully"),
            @ApiResponse(responseCode = "400", description = """
                                                             Returns code:
                                                              3: if user does not exists
                                                              4: if an unknown category is present
                                                              5: if save failed"""),

    })
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody ResponseEntity<CreateBudgetResponse> createNewBudget(@Valid @RequestBody CreateBudgetDTO request) {
        Budget newBudget = budgetService.createBudget(
                request.getBudgetUserID(),
                request.getName(),
                request.getDescription(),
                request.getBudgetAmountLimit(),
                request.getBudgetDate(),
                request.getExpenseList()
        );
        if (newBudget == null) throw new BudgetCreateException();
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(new CreateBudgetResponse(
                                     newBudget.getName(),
                                     newBudget.getId()
                                              .toString(),
                                     true
                             ));
    }

    @GetMapping(path = "/{userId}", params = {"page", "size"})
    public @ResponseBody ResponseEntity<CurrentMonthBudgetListResponse> getCurrentBudgetList(
            @UUID @PathVariable String userId,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            @RequestParam(name = "page", defaultValue = "0") int page
    ) {

        budgetUserService.getUser(userId);

        Page<Budget> budgets = budgetService.getUserBudgets(
                userId,
                page,
                size
        );

        CurrentMonthBudgetListResponse currentMonthBudgetListResponse =
                new CurrentMonthBudgetListResponse(
                        Pagination.fromPage(
                                budgets,
                                page
                        ),
                        budgets.stream()
                               .map(budget -> new CurrentMonthBudget(
                                       budget.getId()
                                             .toString(),
                                       budget.getName(),
                                       budget.getCreateDate(),
                                       budget.getBudgetCategories()
                                             .stream()
                                             .map(category -> new CurrentMonthCategories(
                                                     category.getBudgetCategory()
                                                             .getColor(),
                                                     category.getBudgetCategory()
                                                             .getName()
                                             ))
                                             .toList()
                               ))
                               .toList()
                );
        return ResponseEntity.ok(currentMonthBudgetListResponse);
    }

    @GetMapping(path = "/item/{budgetId}")
    @Operation(summary = "Returns a budget details with its categories", security = @SecurityRequirement(name = "Auth JWT"), method = "GET", responses = {
            @ApiResponse(responseCode = "200", description = "Budget " +
                                                             "fetch " +
                                                             "successfully"),
            @ApiResponse(responseCode = "400", description = """
                                                             Returns code:
                                                              3: if budget does not exists"""),

    })
    public @ResponseBody ResponseEntity<BudgetItem> getUserBudget(
            @UUID @PathVariable String budgetId
    ) {
        Budget budget = budgetService.getUserBudget(budgetId);
        return ResponseEntity.ok(new BudgetItem(
                budget.getId()
                      .toString(),
                budget.getName(),
                budget.getDescription(),
                budget.getBudgetAmountLimit(),
                budget.getTotalSpending(),
                budget.getCreateDate(),
                budget.getBudgetCategories()
                      .stream()
                      .map(budgetCategory -> new BudgetItemCategories(
                              budgetCategory.getBudgetCategory()
                                            .getId(),
                              budgetCategory.getBudgetCategory()
                                            .getName(),
                              budgetCategory.getBudgetCategory()
                                            .getColor(),
                              budgetCategory.getAmountLimit(),
                              budgetCategory.getCurrentSpending()
                      ))
                      .toList()
        ));
    }

    @GetMapping(
            path = "/item-expenses/{budgetId}",
            params = {"size", "page"}
    )
    @Operation(
            summary = "Returns a list of expenses related to a budget",
            parameters = {
                    @Parameter(
                            name = "size",
                            description = "size of " +
                                          "the page"
                    ),
                    @Parameter(
                            name = "page",
                            description = "Page requested"
                    )
            },
            security = @SecurityRequirement(name = "Auth JWT"),
            method = "GET",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description =
                                    "Budget expenses" +
                                    " list" + " " +
                                    "fetch " +
                                    "successfully"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = """
                                          Returns code:
                                           3: if budget does not exists"""
                    ),

            })
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody ResponseEntity<ExpenseList> getUserBudgetExpenses(
            @UUID @PathVariable String budgetId,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            @RequestParam(name = "page", defaultValue = "0") int page
    ) {
        Page<BudgetExpense> expenses =
                budgetService.getBudgetExpenses(
                        budgetId,
                        page,
                        size
                );
        return ResponseEntity.ok(new ExpenseList(
                                         expenses.stream()
                                                 .map(expense -> new Expense(
                                                         expense.getId(),
                                                         expense.getName(),
                                                         expense.getAmount(),
                                                         expense.getExpenseDate(),
                                                         new ExpenseCategory(
                                                                 expense.getBudgetCategory()
                                                                        .getName(),
                                                                 expense.getBudgetCategory()
                                                                        .getColor()
                                                         )
                                                 ))
                                                 .toList(),
                                         Pagination.fromPage(
                                                 expenses,
                                                 page
                                         )
                                 )
        );
    }

    @PostMapping("/create-expense")
    @Operation(summary = "Creates a new budget expense for an specific budget",
            security = @SecurityRequirement(name = "Auth JWT"), method = "POST",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Budget " +
                                                                     "created " +
                                                                     "successfully"),
                    @ApiResponse(responseCode = "400", description = """
                                                                     Returns code:
                                                                      3: if budget does not exists,
                                                                      4:"""),

            })
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody ResponseEntity<Expense> addExpense(
            @Valid @RequestBody
            CreateExpenseDTO createExpenseDTO
    ) {
        BudgetExpense expense =
                budgetService.createExpense(
                        createExpenseDTO.getName(),
                        createExpenseDTO.getDate(),
                        createExpenseDTO.getAmount(),
                        createExpenseDTO.getBudget(),
                        createExpenseDTO.getCategory()
                );
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(new Expense(
                                           expense.getId(),
                                           expense.getName(),
                                           expense.getAmount(),
                                           expense.getExpenseDate(),
                                           new ExpenseCategory(
                                                   expense.getBudgetCategory()
                                                          .getName(),
                                                   expense.getBudgetCategory()
                                                          .getColor()
                                           )
                                   )
                             );
    }
}
