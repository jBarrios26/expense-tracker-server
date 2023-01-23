package com.jorgebarrios.expensetracker.category.controller;

import com.jorgebarrios.expensetracker.category.model.BudgetCategory;
import com.jorgebarrios.expensetracker.category.model.dto.CreateCategoryDTO;
import com.jorgebarrios.expensetracker.category.model.response.CategoryListResponse;
import com.jorgebarrios.expensetracker.category.model.response.CreateCategoryResponse;
import com.jorgebarrios.expensetracker.category.repository.BudgetCategoryRepository;
import com.jorgebarrios.expensetracker.category.service.BudgetCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("api/category")
@RequiredArgsConstructor
@RestController
public class BudgetCategoryController {

    private final BudgetCategoryService budgetCategoryService;
    private final BudgetCategoryRepository budgetCategoryRepository;

    @GetMapping("/{userId}")
    @Operation(
            method = "GET",
            description =
                    "Retrieves all the categories available for an " +
                    "specific user",
            parameters = {
                    @Parameter(
                            name = "userId",
                            description = "UUID that " +
                                          "identifies the" +
                                          " user",
                            required = true
                    ),
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Returns the " +
                                          "list " + "of " +
                                          "categories or empty if users doesnt have categories available."
                    ),
            }
    )
    public ResponseEntity<CategoryListResponse> getCategoriesForUser(
            @PathVariable(required = true) @Pattern(regexp =
                    "^[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0" +
                    "-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a" +
                    "-f]{12}$", message = "UserId should be a valid UUID") String userId
    ) {
        List<BudgetCategory> userCategories =
                budgetCategoryService.getUserCategories(userId);
        List<BudgetCategory> generalCategories =
                budgetCategoryService.getGeneralCategories();
        ArrayList<BudgetCategory> categories =
                new ArrayList<BudgetCategory>(generalCategories);
        categories.addAll(userCategories);
        return ResponseEntity.ok(new CategoryListResponse(
                true,
                categories
        ));
    }

    @PostMapping("/")
    @Operation(
            method = "POST",
            description = "Creates a new category for a user.",
            requestBody =
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required =
                            true
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Category was " +
                                          "successfully " +
                                          "created"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "User was not found"
                    )
            }
    )
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CreateCategoryResponse> createCategory(
            @Valid @RequestBody CreateCategoryDTO request
    ) {
        final BudgetCategory category = budgetCategoryService.createNewCategory(
                request.getName(),
                request.getColor(),
                request.getUserId()
        );
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(new CreateCategoryResponse(
                                     category,
                                     true
                             ));
    }

    @GetMapping("/user-categories/{userId}")
    @Operation(
            method = "GET",
            description =
                    "Retrieves all the categories made by an " +
                    "specific user",
            parameters = {
                    @Parameter(
                            name = "userId",
                            description = "UUID that " +
                                          "identifies the" +
                                          " user",
                            required = true
                    ),
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Returns the " +
                                          "list " + "of " +
                                          "categories or empty if users doesnt have categories available."
                    ),
            }
    )
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CategoryListResponse> getUserMadeCategories(
            @UUID(
                    message = "userId must be an UUID") @PathVariable String userId
    ) {
        List<BudgetCategory> userCategories =
                budgetCategoryService.getUserCategories(userId);
        return ResponseEntity.ok(new CategoryListResponse(
                true,
                userCategories
        ));
    }
}
