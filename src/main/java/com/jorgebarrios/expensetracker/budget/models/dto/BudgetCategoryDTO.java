package com.jorgebarrios.expensetracker.budget.models.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BudgetCategoryDTO {
    @NotNull
    private String categoryId;
    @NotNull
    @Min(value = 0, message = "Category should have a non negative limit " +
                              "amount")
    private Double limit;

    public BudgetCategoryDTO(
            String categoryId,
            Double limit
                            ) {
        this.categoryId = categoryId;
        this.limit = limit;
    }
}
