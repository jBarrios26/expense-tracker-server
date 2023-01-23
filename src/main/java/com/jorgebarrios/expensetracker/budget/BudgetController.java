package com.jorgebarrios.expensetracker.budget;

import com.jorgebarrios.expensetracker.budget.exception.BudgetCreateException;
import com.jorgebarrios.expensetracker.budget.models.dto.CreateBudgetDTO;
import com.jorgebarrios.expensetracker.budget.models.response.CreateBudgetResponse;
import com.jorgebarrios.expensetracker.budget.models.response.CurrentMonthBudget;
import com.jorgebarrios.expensetracker.budget.models.response.CurrentMonthBudgetListResponse;
import com.jorgebarrios.expensetracker.budget.models.response.CurrentMonthCategories;
import com.jorgebarrios.expensetracker.common.model.Pagination;
import com.jorgebarrios.expensetracker.user.BudgetUserRepository;
import com.jorgebarrios.expensetracker.user.BudgetUserService;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(
            summary = "Create new budget for associated user",
            security = @SecurityRequirement(name = "Auth JWT"),
            method = "POST",
            responses = {
                    @ApiResponse(responseCode = "201",
                            description = "Create " +
                                          "budget " +
                                          "Successfully"),
                    @ApiResponse(
                            responseCode = "400",
                            description =
                                    """
                                    Returns code:
                                     3: if user does not exists
                                     4: if an unknown category is present
                                     5: if save failed"""),

            }
    )
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody ResponseEntity<CreateBudgetResponse> createNewBudget(@Valid @RequestBody CreateBudgetDTO request) {
        Budget newBudget =
                budgetService.createBudget(
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
            @RequestParam(name = "size", defaultValue = "10", required =
                    false) int size,
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
                                       budget.getId().toString(),
                                       budget.getName(),
                                       budget.getCreateDate(),
                                       budget.getBudgetCategories()
                                             .stream()
                                             .map(category -> new CurrentMonthCategories(category.getBudgetCategory()
                                                                                                 .getColor(),
                                                                                         category.getBudgetCategory()
                                                                                                 .getName()
                                             )).toList()
                               ))
                               .toList()
                );
        return ResponseEntity.ok(currentMonthBudgetListResponse);
    }
}
