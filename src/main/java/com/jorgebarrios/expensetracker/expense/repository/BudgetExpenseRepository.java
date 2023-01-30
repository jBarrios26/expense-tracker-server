package com.jorgebarrios.expensetracker.expense.repository;

import com.jorgebarrios.expensetracker.expense.BudgetExpense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BudgetExpenseRepository extends JpaRepository<BudgetExpense,
        UUID> {
    @Query("SELECT b FROM BudgetExpense b where b.budget.id = ?1")
    Page<BudgetExpense> findBudgetExpensesFromBudget(
            UUID budgetId,
            Pageable pageable
    );
}
