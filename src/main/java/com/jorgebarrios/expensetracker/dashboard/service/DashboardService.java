package com.jorgebarrios.expensetracker.dashboard.service;

import com.jorgebarrios.expensetracker.dashboard.model.CategoryInfo;
import com.jorgebarrios.expensetracker.dashboard.model.SpentItem;
import com.jorgebarrios.expensetracker.expense.BudgetExpense;
import com.jorgebarrios.expensetracker.expense.model.CategoryInfoDTO;
import com.jorgebarrios.expensetracker.expense.model.DayExpenseDTO;
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
        List<TotalSpentDTO> monthExpenseList =
                budgetExpenseRepository.getBudgetExpenseByMonth(
                                               userId,
                                               year
                                       )
                                       .stream()
                                       .sorted(Comparator.comparing(TotalSpentDTO::getDateOfExpense))
                                       .toList();
        List<TotalSpentDTO> expensesByDayInMonth = new ArrayList<>();
        int dayExpenseListIndex = 0;
        for (int day = 1; day <= 12; day++) {
            dayExpenseListIndex = getDayExpenseListIndex(
                    monthExpenseList,
                    expensesByDayInMonth,
                    dayExpenseListIndex,
                    day
            );
        }
        return expensesByDayInMonth.stream()
                                   .map(expense -> new TotalSpentDTO(
                                                expense.getDateOfExpense(),
                                                expense.getTotalSpent()
                                        )
                                   )
                                   .toList();

    }

    private int getDayExpenseListIndex(
            List<TotalSpentDTO> monthExpenseList,
            List<TotalSpentDTO> expensesByDayInMonth,
            int dayExpenseListIndex,
            int day
    ) {
        if (dayExpenseListIndex >= monthExpenseList.size()) {
            expensesByDayInMonth.add(TotalSpentDTO.empty(day));
            return dayExpenseListIndex;
        }
        expensesByDayInMonth.add(monthExpenseList.get(dayExpenseListIndex)
                                                 .getDateOfExpense() == day
                                 ? monthExpenseList.get(dayExpenseListIndex)
                                 : TotalSpentDTO.empty(day));
        if (monthExpenseList.get(dayExpenseListIndex)
                            .getDateOfExpense() == day)
            dayExpenseListIndex += 1;
        return dayExpenseListIndex;
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
            dayExpenseListIndex = getDayExpenseListIndex(
                    dayExpenseList,
                    expensesByDayInMonth,
                    dayExpenseListIndex,
                    day
            );
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

    public List<DayExpenseDTO> getTotalSpentByWeekDay(
            final UUID userId
    ) {

        List<DayExpenseDTO> weekDayExpenses =
                budgetExpenseRepository.getTotalSpentByWeekday(userId)
                                       .stream()
                                       .sorted(Comparator.comparing(DayExpenseDTO::getWeekDay))
                                       .toList();
        List<DayExpenseDTO> weekDayAllExpenses = new ArrayList<>();

        int weekday = 0;
        for (int day = 0; day < 7; day++) {

            if (day >= weekDayExpenses.size()) {
                weekDayAllExpenses.add(DayExpenseDTO.empty(day));
                continue;
            }

            weekDayAllExpenses.add(weekDayExpenses.get(weekday)
                                                  .getWeekDay() == day
                                   ? weekDayExpenses.get(weekday)
                                   : DayExpenseDTO.empty(day));
            if (weekDayExpenses.get(weekday)
                               .getWeekDay() == day)
                weekday += 1;
        }
        return weekDayAllExpenses.stream()
                                 .map(expense -> new DayExpenseDTO(
                                              (double) expense.getWeekDay(),
                                              expense.getSpentTotal()
                                      )
                                 )
                                 .toList();
    }
}
