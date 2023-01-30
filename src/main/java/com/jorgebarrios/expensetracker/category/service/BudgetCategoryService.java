package com.jorgebarrios.expensetracker.category.service;

import com.jorgebarrios.expensetracker.category.exception.BudgetCategoryNotFound;
import com.jorgebarrios.expensetracker.category.model.BudgetCategory;
import com.jorgebarrios.expensetracker.category.repository.BudgetCategoriesRepository;
import com.jorgebarrios.expensetracker.category.repository.BudgetCategoryRepository;
import com.jorgebarrios.expensetracker.common.exception.BudgetUserNotFoundException;
import com.jorgebarrios.expensetracker.user.BudgetUser;
import com.jorgebarrios.expensetracker.user.BudgetUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BudgetCategoryService {

    private final BudgetCategoriesRepository budgetCategoriesRepository;
    private final BudgetCategoryRepository budgetCategoryRepository;
    private final BudgetUserRepository budgetUserRepository;

    public List<BudgetCategory> getGeneralCategories() {
        return budgetCategoryRepository.findGeneralCategories();
    }

    public List<BudgetCategory> getUserCategories(final String userId) {
        return budgetCategoryRepository.findUserCategories(UUID.fromString(userId));
    }

    public BudgetCategory createNewCategory(
            final String name,
            final String color,
            final String userId
    ) {
        Optional<BudgetUser> user =
                budgetUserRepository.findById(UUID.fromString(userId));
        if (user.isEmpty()) {
            throw new BudgetUserNotFoundException(userId);
        }
        BudgetCategory category = new BudgetCategory(
                name,
                color,
                user.get()
        );
        return budgetCategoryRepository.save(category);
    }

    public BudgetCategory getCategory(final UUID id) {
        Optional<BudgetCategory> category =
                budgetCategoryRepository.findById(id);
        if (category.isEmpty()) {
            throw new BudgetCategoryNotFound(id.toString());
        }
        return category.get();
    }


}
