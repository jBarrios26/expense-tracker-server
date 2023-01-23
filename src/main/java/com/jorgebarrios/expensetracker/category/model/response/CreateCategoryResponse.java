package com.jorgebarrios.expensetracker.category.model.response;

import com.jorgebarrios.expensetracker.category.model.BudgetCategory;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateCategoryResponse {
    private BudgetCategory category;
    private  Boolean success;

    public CreateCategoryResponse(
            BudgetCategory category,
            Boolean success
                                 ) {
        this.category = category;
        this.success = success;
    }
}
