package com.jorgebarrios.expensetracker.expense.service;

import com.jorgebarrios.expensetracker.budget.Budget;
import com.jorgebarrios.expensetracker.budget.repository.BudgetRepository;
import com.jorgebarrios.expensetracker.category.model.BudgetCategories;
import com.jorgebarrios.expensetracker.category.model.BudgetCategory;
import com.jorgebarrios.expensetracker.category.repository.BudgetCategoriesRepository;
import com.jorgebarrios.expensetracker.expense.BudgetExpense;
import com.jorgebarrios.expensetracker.expense.exception.InvalidExpenseDateException;
import com.jorgebarrios.expensetracker.expense.exception.UnknownExpenseException;
import com.jorgebarrios.expensetracker.expense.model.BudgetExpenseDTO;
import com.jorgebarrios.expensetracker.expense.repository.BudgetExpenseRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BudgetExpenseService {
    private final BudgetExpenseRepository budgetExpenseRepository;
    private final BudgetCategoriesRepository budgetCategoriesRepository;
    private final BudgetRepository budgetRepository;

    public BudgetExpense editExpense(
            final String expenseId,
            final BudgetExpenseDTO updatedExpense
    ) {

        Optional<BudgetExpense> expenseOptional =
                budgetExpenseRepository.findById(UUID.fromString(expenseId));
        if (expenseOptional.isEmpty()) {
            throw new UnknownExpenseException(expenseId);
        }

        BudgetExpense expense = expenseOptional.get();
        Budget budget = expense.getBudget();
        BudgetCategory categories = expense.getBudgetCategory();


        Double oldAmount = expense.getAmount();

        if (updatedExpense.getAmount() != null) {
            expense.setAmount(updatedExpense.getAmount());
            budget.setTotalSpending(budget.getTotalSpending() - oldAmount +
                                    updatedExpense.getAmount());
            budgetRepository.save(budget);

            Optional<BudgetCategories> budgetCategoriesOptional =
                    budgetCategoriesRepository.findBudgetCategoriesByBudgetAndBudgetCategory(
                            budget,
                            expense.getBudgetCategory()
                    );

            if (budgetCategoriesOptional.isPresent()) {
                BudgetCategories category = budgetCategoriesOptional.get();
                category.setCurrentSpending(
                        category.getCurrentSpending() - oldAmount +
                        updatedExpense.getAmount());
                budgetCategoriesRepository.save(category);
            }
        }
        if (updatedExpense.getName() != null) {
            expense.setName(updatedExpense.getName());

        }
        if (updatedExpense.getExpenseDate() != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(expense.getExpenseDate());
            int oldMonth = cal.get(Calendar.MONTH);
            int oldYear = cal.get(Calendar.YEAR);
            cal.setTime(updatedExpense.getExpenseDate());
            int newMonth = cal.get(Calendar.MONTH);
            int newYear = cal.get(Calendar.YEAR);

            if (newMonth != oldMonth || newYear != oldYear) {
                throw new InvalidExpenseDateException(updatedExpense.getExpenseDate());
            }
            expense.setExpenseDate(updatedExpense.getExpenseDate());
        }
        return budgetExpenseRepository.save(expense);
    }

    @Transactional
    public boolean removeExpense(
            final String expenseId
    ) {

        Optional<BudgetExpense> expenseOptional =
                budgetExpenseRepository.findById(UUID.fromString(expenseId));
        if (expenseOptional.isEmpty()) {
            throw new UnknownExpenseException(expenseId);
        }

        BudgetExpense expense = expenseOptional.get();
        Budget budget = expense.getBudget();

        Double oldAmount = expense.getAmount();
        budget.setTotalSpending(budget.getTotalSpending() - oldAmount);
        budget.getBudgetExpenses()
              .remove(expense);
        budgetRepository.save(budget);

        Optional<BudgetCategories> budgetCategoriesOptional =
                budgetCategoriesRepository.findBudgetCategoriesByBudgetAndBudgetCategory(
                        budget,
                        expense.getBudgetCategory()
                );

        if (budgetCategoriesOptional.isPresent()) {
            BudgetCategories category = budgetCategoriesOptional.get();
            category.setCurrentSpending(
                    category.getCurrentSpending() - oldAmount);
            budgetCategoriesRepository.save(category);
        }

        budgetExpenseRepository.delete(expense);
        return true;
    }
}
