package com.jorgebarrios.expensetracker.budget.repository;

import com.jorgebarrios.expensetracker.budget.Budget;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.UUID;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, UUID> {

    @Query("SELECT budget FROM Budget budget " +
           "WHERE (budget" +
           ".budgetPeriod <= ?2 " +
           "and budget.budgetPeriod >= ?3) and budget.budgetUser.id = ?1")
    Page<Budget> findCurrentMonthBudgetOfUser(
            final UUID userId,
            final
            Date to,
            final Date from,
            Pageable pageable
    );
}
