package com.jorgebarrios.expensetracker.budget.service;

import com.jorgebarrios.expensetracker.budget.Budget;
import com.jorgebarrios.expensetracker.budget.exception.BudgetNotFoundException;
import com.jorgebarrios.expensetracker.budget.exception.UnknownBudgetCategoryException;
import com.jorgebarrios.expensetracker.budget.models.dto.BudgetCategoryDTO;
import com.jorgebarrios.expensetracker.budget.repository.BudgetRepository;
import com.jorgebarrios.expensetracker.category.model.BudgetCategories;
import com.jorgebarrios.expensetracker.category.model.BudgetCategory;
import com.jorgebarrios.expensetracker.category.repository.BudgetCategoriesRepository;
import com.jorgebarrios.expensetracker.category.repository.BudgetCategoryRepository;
import com.jorgebarrios.expensetracker.category.service.BudgetCategoryService;
import com.jorgebarrios.expensetracker.common.exception.BudgetUserNotFoundException;
import com.jorgebarrios.expensetracker.expense.BudgetExpense;
import com.jorgebarrios.expensetracker.expense.repository.BudgetExpenseRepository;
import com.jorgebarrios.expensetracker.user.BudgetUser;
import com.jorgebarrios.expensetracker.user.BudgetUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.*;

@Service
@RequiredArgsConstructor
public class BudgetService {
    private final BudgetRepository budgetRepository;
    private final BudgetUserRepository budgetUserRepository;
    private final BudgetCategoryRepository budgetCategoryRepository;
    private final BudgetCategoriesRepository budgetCategoriesRepository;
    private final BudgetExpenseRepository budgetExpenseRepository;

    private final BudgetCategoryService budgetCategoryService;

    public Budget createBudget(
            final String userId,
            final String name,
            final String description,
            final Double limitAmount,
            final Date budgetPeriodDate,
            final List<BudgetCategoryDTO> categoryList
    ) {
        Optional<BudgetUser> budgetUser =
                budgetUserRepository.findById(UUID.fromString(userId));
        if (budgetUser.isEmpty()) {
            throw new BudgetUserNotFoundException(userId);
        }
        List<UUID> categoriesIds =
                categoryList.stream()
                            .map(category -> UUID.fromString(category.getCategoryId()))
                            .toList();
        List<BudgetCategory> categories =
                budgetCategoryRepository.findAllById(categoriesIds);

        List<UUID> notPresentIds = categoriesIds.stream()
                                                .filter(id -> !categories.stream()
                                                                         .map(BudgetCategory::getId)
                                                                         .toList()
                                                                         .contains(id))
                                                .toList();
        if (!notPresentIds.isEmpty()) {
            throw new UnknownBudgetCategoryException(notPresentIds);
        }
        Budget budget = new Budget(
                name,
                description,
                limitAmount,
                budgetPeriodDate,
                budgetUser.get()
        );

        Budget savedBudget = budgetRepository.save(budget);

        List<BudgetCategories> budgetCategories = categories.stream()
                                                            .map((category) -> new BudgetCategories(
                                                                    savedBudget,
                                                                    category,
                                                                    categoryList.stream()
                                                                                .filter(c -> c.getCategoryId()
                                                                                              .equals(category.getId()
                                                                                                              .toString()))
                                                                                .findFirst()
                                                                                .orElse(new BudgetCategoryDTO(
                                                                                        "",
                                                                                        0.0
                                                                                ))
                                                                                .getLimit()
                                                            ))
                                                            .toList();
        budgetCategoriesRepository.saveAll(budgetCategories);
        savedBudget.setBudgetCategories(Set.copyOf(budgetCategories));
        return savedBudget;
    }

    public Page<Budget> getUserBudgets(
            final String userId,
            final Integer page,
            final Integer size
    ) {
        LocalDateTime currentDate = LocalDateTime.now();
        Month currentMonth = currentDate.getMonth();
        int lastDayOfCurrentMonth = (
                                            currentDate.toLocalDate()
                                                       .isLeapYear()
                                    ) ?
                                    currentMonth.maxLength()
                                      : currentMonth.minLength();
        int currentYear = currentDate.getYear();
        LocalDate fromDate = LocalDate.of(
                currentYear,
                currentMonth,
                1
        );
        LocalDate toDate = LocalDate.of(
                currentYear,
                currentMonth,
                lastDayOfCurrentMonth
        );
        Date from =
                Date.from(fromDate.atStartOfDay(ZoneId.systemDefault())
                                  .toInstant());
        Date to =
                Date.from(toDate.atStartOfDay(ZoneId.systemDefault())
                                .toInstant());
        return budgetRepository.findCurrentMonthBudgetOfUser(
                UUID.fromString(userId),
                to,
                from,
                PageRequest.of(
                        page,
                        size
                )
        );
    }

    public Budget getUserBudget(final String budgetId) {
        Optional<Budget> budget =
                budgetRepository.findById(UUID.fromString(budgetId));
        if (budget.isEmpty()) {
            throw new BudgetNotFoundException(budgetId);
        }
        return budget.get();
    }

    public Page<BudgetExpense> getBudgetExpenses(
            final String budgetId,
            final int page,
            final int size
    ) {
        return budgetExpenseRepository.findBudgetExpensesFromBudget(
                UUID.fromString(budgetId),
                PageRequest.of(
                        page,
                        size
                )
        );
    }

    public BudgetExpense createExpense(
            final String name,
            final Date expenseDate,
            final Double amount,
            final UUID budget,
            final UUID category
    ) {
        Budget budgetEntity = getUserBudget(budget.toString());
        BudgetCategory categoryEntity =
                budgetCategoryService.getCategory(category);
        BudgetExpense expense = new BudgetExpense(
                name,
                expenseDate,
                amount,
                budgetEntity,
                categoryEntity
        );
        return budgetExpenseRepository.save(expense);
    }
}
