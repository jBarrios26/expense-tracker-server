package com.jorgebarrios.expensetracker.category.repository;

import com.jorgebarrios.expensetracker.category.model.BudgetCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BudgetCategoryRepository extends JpaRepository<BudgetCategory,
        UUID> {

    @Query("SELECT category from BudgetCategory category WHERE category.userMade = false")
    List<BudgetCategory> findGeneralCategories();

    @Query("SELECT category from BudgetCategory  category where category" +
           ".budgetUser.id = ?1")
    List<BudgetCategory> findUserCategories(final UUID userId);
}
