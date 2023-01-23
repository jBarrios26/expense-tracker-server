package com.jorgebarrios.expensetracker.category.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateCategoryDTO {
    @NotBlank
    private String name;
    @NotBlank
    private String color;
    @Pattern(regexp = "^[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0" +
                      "-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a" +
                      "-f]{12}$",
            message = "UserId should be a valid UUID")
    private String userId;
}
