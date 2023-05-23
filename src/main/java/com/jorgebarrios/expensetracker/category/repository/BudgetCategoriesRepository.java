package com.jorgebarrios.expensetracker.category.repository;

import com.jorgebarrios.expensetracker.budget.Budget;
import com.jorgebarrios.expensetracker.category.model.BudgetCategories;
import com.jorgebarrios.expensetracker.category.model.BudgetCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BudgetCategoriesRepository extends
                                            JpaRepository<BudgetCategories, Long> {
    Optional<BudgetCategories> findBudgetCategoriesByBudgetAndBudgetCategory(
            Budget budget,
            BudgetCategory budgetCategory
    );
    

    void deleteBudgetCategoriesByBudget(Budget budget);
}
