package com.jorgebarrios.expensetracker.category.model.response;

import com.jorgebarrios.expensetracker.category.model.BudgetCategory;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CategoryListResponse {
    private Boolean success;
    private List<BudgetCategory> categories;

    public CategoryListResponse(
            final Boolean success,
            final List<BudgetCategory> categories
                               ) {
        this.success = success;
        this.categories = categories;
    }

}
