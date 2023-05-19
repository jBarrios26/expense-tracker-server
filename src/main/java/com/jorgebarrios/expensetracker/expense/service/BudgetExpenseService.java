package com.jorgebarrios.expensetracker.expense.service;

import com.jorgebarrios.expensetracker.budget.Budget;
import com.jorgebarrios.expensetracker.budget.repository.BudgetRepository;
import com.jorgebarrios.expensetracker.category.model.BudgetCategories;
import com.jorgebarrios.expensetracker.category.model.BudgetCategory;
import com.jorgebarrios.expensetracker.expense.BudgetExpense;
import com.jorgebarrios.expensetracker.expense.exception.InvalidExpenseDateException;
import com.jorgebarrios.expensetracker.expense.exception.UnknownExpenseException;
import com.jorgebarrios.expensetracker.expense.model.BudgetExpenseDTO;
import com.jorgebarrios.expensetracker.expense.repository.BudgetExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BudgetExpenseService {
    private final BudgetExpenseRepository budgetExpenseRepository;
    private final BudgetRepository budgetRepository;

    public BudgetExpense editExpense (final String expenseId,
            final BudgetExpenseDTO updatedExpense) {

        Optional<BudgetExpense> expenseOptional =
                budgetExpenseRepository.findById(UUID.fromString(expenseId));
        if (expenseOptional.isEmpty()) {
            throw  new UnknownExpenseException(expenseId);
        }

        BudgetExpense expense = expenseOptional.get();
        Budget budget = expense.getBudget();
        BudgetCategory categories = expense.getBudgetCategory();



        Double oldAmount = expense.getAmount();

        if (updatedExpense.getAmount() != null) {
            expense.setAmount(updatedExpense.getAmount());
            budget.setTotalSpending(budget.getTotalSpending() - oldAmount + updatedExpense.getAmount());
            budgetRepository.save(budget);
        }
        if (updatedExpense.getName() != null) {
            expense.setName(updatedExpense.getName());

        }
        if (updatedExpense.getExpenseDate() != null) {
            LocalDate newDate =  LocalDate.ofEpochDay(updatedExpense.getExpenseDate().getTime());
            LocalDate oldDate =  LocalDate.ofEpochDay(expense.getExpenseDate().getTime());
            if (!newDate.getMonth().equals(oldDate.getMonth()) && newDate.getYear() != oldDate.getYear()) {
                throw new InvalidExpenseDateException(updatedExpense.getExpenseDate());
            }
            expense.setExpenseDate(updatedExpense.getExpenseDate());
        }

        BudgetExpense newBudgetExpense =  budgetExpenseRepository.save(expense);




        return  newBudgetExpense;
     }
}
