package com.jorgebarrios.expensetracker.budget.models.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class CreateExpenseDTO {
    @NotBlank
    private String name;

    private Date date;
    @Min(value = 0, message = "Amount shoud be positive and greater than zero.")
    private Double amount;
    private UUID budget;

    private UUID category;
}
