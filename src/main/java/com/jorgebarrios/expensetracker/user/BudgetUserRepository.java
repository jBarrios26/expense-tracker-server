package com.jorgebarrios.expensetracker.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BudgetUserRepository
        extends JpaRepository<BudgetUser, UUID> {

    @Query("SELECT s FROM BudgetUser s WHERE s.email = ?1")
    Optional<BudgetUser> findBudgetUserByEmail(final String email);


}
