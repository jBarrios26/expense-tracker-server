package com.jorgebarrios.expensetracker.user;

import com.jorgebarrios.expensetracker.common.exception.BudgetUserNotFoundException;
import com.jorgebarrios.expensetracker.user.exceptions.UserAlreadyRegisterException;
import com.jorgebarrios.expensetracker.user.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class BudgetUserService {

    private final BudgetUserRepository budgetUserRepository;

    @Autowired
    public BudgetUserService(BudgetUserRepository budgetUserRepository) {
        this.budgetUserRepository = budgetUserRepository;
    }

    public boolean saveBudgetUser(
            final String name,
            final String lastName,
            final String email,
            final String password,
            final Byte sex
                                 ) {
        Optional<BudgetUser> previousUser =
                budgetUserRepository.findBudgetUserByEmail(email);
        if (previousUser.isPresent()) {
            throw new UserAlreadyRegisterException(email);
        }
        BudgetUser newUser = budgetUserRepository.save(new BudgetUser(
                name,
                lastName,
                email,
                password,
                sex
        ));
        return true;
    }

    public boolean login(
            final String email,
            final String password
                        ) {
        Optional<BudgetUser> user =
                budgetUserRepository.findBudgetUserByEmail(email);
        if (user.isEmpty()) {
            throw new UserNotFoundException(email);
        }
        return user.get()
                   .getPassword()
                   .equals(password);
    }

    public BudgetUser getUser(final String userId) {
        Optional<BudgetUser> user =
                budgetUserRepository.findById(UUID.fromString(userId));
        if (user.isEmpty()) {
            throw new BudgetUserNotFoundException(userId);
        }
        return user.get();
    }
}
