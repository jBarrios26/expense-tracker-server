package com.jorgebarrios.expensetracker.expense.repository;

import com.jorgebarrios.expensetracker.budget.Budget;
import com.jorgebarrios.expensetracker.expense.BudgetExpense;
import com.jorgebarrios.expensetracker.expense.model.CategoryInfoDTO;
import com.jorgebarrios.expensetracker.expense.model.DayExpenseDTO;
import com.jorgebarrios.expensetracker.expense.model.TotalSpentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BudgetExpenseRepository extends JpaRepository<BudgetExpense,
        UUID> {
    @Query("SELECT b FROM BudgetExpense b where b.budget.id = ?1")
    Page<BudgetExpense> findBudgetExpensesFromBudget(
            UUID budgetId,
            Pageable pageable
    );

    @Query("SELECT e FROM BudgetExpense e WHERE e.budget.budgetUser.id = ?1 ")
    List<BudgetExpense> findLastExpenses(UUID userId);

    @Query(
            "SELECT new com.jorgebarrios.expensetracker.expense.model" +
            ".TotalSpentDTO(extract(month from e.expenseDate), SUM(e.amount) )" +
            "FROM BudgetExpense e JOIN Budget b on(b.id = e.budget) " +
            "WHERE b.budgetUser.id = ?1 " +
            "GROUP BY extract (month from e.expenseDate), extract(year from e" +
            ".expenseDate) " +
            "HAVING extract(year from e.expenseDate) = ?2 "

    )
    List<TotalSpentDTO> getBudgetExpenseByMonth(
            UUID userId,
            Integer year
    );

    @Query(
            "SELECT new com.jorgebarrios.expensetracker.expense.model" +
            ".TotalSpentDTO(extract(day from e.expenseDate), SUM(e.amount) )" +
            "FROM BudgetExpense e JOIN Budget b on(b.id = e.budget) " +
            "WHERE b.budgetUser.id = ?1 " +
            "GROUP BY extract(day from e.expenseDate), extract (month from e" +
            ".expenseDate), extract(year from e.expenseDate) " +
            "HAVING extract(year from e.expenseDate) = ?3 AND extract(month " +
            "from e.expenseDate) = ?2 "

    )
    List<TotalSpentDTO> getBudgetExpenseByDay(
            UUID userId,
            Integer month,
            Integer year
    );

    @Query(
            "SELECT new com.jorgebarrios.expensetracker.expense.model" +
            ".CategoryInfoDTO(MIN(bc.name), MIN(bc.color), SUM(e.amount), CAST(COUNT(e.budgetCategory.id) as INTEGER ))" +
            "FROM BudgetExpense e JOIN Budget b ON b.id = e.budget JOIN BudgetCategory bc ON bc.id = e.budgetCategory.id " +
            "WHERE b.budgetUser.id = ?1 " +
            "GROUP BY e.budgetCategory.id "
    )
    List<CategoryInfoDTO> getTopCategoryInExpensesByUser(UUID userId);

    @Query(
            "SELECT new com.jorgebarrios.expensetracker.expense.model" +
            ".DayExpenseDTO(function('date_part', 'dow', e.expenseDate), SUM(e" +
            ".amount)) " +
            "FROM BudgetExpense e JOIN Budget b ON b.id = e.budget.id " +
            "WHERE b.budgetUser.id = ?1 " +
            "GROUP BY function('date_part', 'dow', e.expenseDate)"
    )
    List<DayExpenseDTO> getTotalSpentByWeekday(UUID userId);

    void deleteBudgetExpenseByBudget(Budget budget);
}
