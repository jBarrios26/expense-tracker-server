package com.jorgebarrios.expensetracker.dashboard.model.response;

import com.jorgebarrios.expensetracker.dashboard.model.CategoryInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TopCategoriesResponse {
    private List<CategoryInfo> topCategories;
}
