package com.jorgebarrios.expensetracker.category.repository;

import com.jorgebarrios.expensetracker.category.model.BudgetCategories;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BudgetCategoriesRepository extends
                                            JpaRepository<BudgetCategories, Long> {
}
