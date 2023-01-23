package com.jorgebarrios.expensetracker.budget.models.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class CreateBudgetDTO {
    @NotNull
    private Date budgetDate;
    @NotNull
    private String name;
    @NotNull
    private String description;
    @NotNull
    @Min(value = 0, message = "budget should be greater than 0")
    private Double budgetAmountLimit;
    @NotNull
    private String budgetUserID;
    
    private List<BudgetCategoryDTO> expenseList;
}
