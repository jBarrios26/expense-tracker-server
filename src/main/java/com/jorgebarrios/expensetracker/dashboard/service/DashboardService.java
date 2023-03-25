package com.jorgebarrios.expensetracker.dashboard.service;

import com.jorgebarrios.expensetracker.dashboard.model.CategoryInfo;
import com.jorgebarrios.expensetracker.dashboard.model.SpentItem;
import com.jorgebarrios.expensetracker.expense.BudgetExpense;
import com.jorgebarrios.expensetracker.expense.model.CategoryInfoDTO;
import com.jorgebarrios.expensetracker.expense.model.TotalSpentDTO;
import com.jorgebarrios.expensetracker.expense.repository.BudgetExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DashboardService {
    private final BudgetExpenseRepository budgetExpenseRepository;

    public List<BudgetExpense> getLastTransactions(final UUID userId) {
        final List<BudgetExpense> expenseList =
                budgetExpenseRepository.findLastExpenses(userId)
                                       .stream()
                                       .sorted(Comparator.comparing(BudgetExpense::getCreateDate)
                                                         .reversed())
                                       .toList();
        return expenseList.subList(
                0,
                Math.min(
                        expenseList.size(),
                        5
                )
        );
    }

    public List<TotalSpentDTO> getTotalSpentByMonth(
            final UUID userId,
            final int year
    ) {
        return budgetExpenseRepository.getBudgetExpenseByMonth(
                                              userId,
                                              year
                                      )
                                      .stream()
                                      .sorted(Comparator.comparing(TotalSpentDTO::getDateOfExpense))
                                      .toList();
    }

    public List<SpentItem> getTotalSpentByDay(
            final UUID userId,
            final int month,
            final int year
    ) {
        LocalDateTime fetchDate = LocalDateTime.of(
                year,
                month,
                1,
                1,
                1
        );
        Month currentMonth = fetchDate.getMonth();
        int lastDayOfCurrentMonth = (
                                            fetchDate.toLocalDate()
                                                     .isLeapYear()
                                    ) ? currentMonth.maxLength()
                                      : currentMonth.minLength();
        List<TotalSpentDTO> dayExpenseList =
                budgetExpenseRepository.getBudgetExpenseByDay(
                                               userId,
                                               month,
                                               year
                                       )
                                       .stream()
                                       .sorted(Comparator.comparing(TotalSpentDTO::getDateOfExpense))
                                       .toList();
        List<TotalSpentDTO> expensesByDayInMonth = new ArrayList<>();
        int dayExpenseListIndex = 0;
        for (int day = 1; day <= lastDayOfCurrentMonth; day++) {
            if (dayExpenseListIndex >= dayExpenseList.size()) {
                expensesByDayInMonth.add(TotalSpentDTO.empty(day));
                continue;
            }
            expensesByDayInMonth.add(dayExpenseList.get(dayExpenseListIndex)
                                                   .getDateOfExpense() == day
                                     ? dayExpenseList.get(dayExpenseListIndex)
                                     : TotalSpentDTO.empty(day));
            if (dayExpenseList.get(dayExpenseListIndex)
                              .getDateOfExpense() == day)
                dayExpenseListIndex += 1;
        }
        return expensesByDayInMonth.stream()
                                   .map(expense -> new SpentItem(
                                                expense.getDateOfExpense()
                                                       .toString(),
                                                expense.getTotalSpent()
                                        )
                                   )
                                   .toList();
    }

    public List<CategoryInfo> getTopCategories(String userId) {
        final List<CategoryInfoDTO> categoryInfoDTOS =
                budgetExpenseRepository.getTopCategoryInExpensesByUser(UUID.fromString(userId))
                                       .stream()
                                       .sorted(Comparator.comparing(CategoryInfoDTO::getNumOfTransaction))
                                       .toList();
        return categoryInfoDTOS.stream()
                               .map(category -> new CategoryInfo(
                                       category.getName(),
                                       category.getColor(),
                                       category.getAmountSpent()
                               ))
                               .toList()
                               .subList(
                                       0,
                                       Math.min(
                                               categoryInfoDTOS.size(),
                                               5
                                       )
                               );
    }
}
