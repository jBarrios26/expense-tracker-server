package com.jorgebarrios.expensetracker.auth.repository;

import com.jorgebarrios.expensetracker.auth.model.RefreshToken;
import com.jorgebarrios.expensetracker.user.BudgetUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthRepository extends JpaRepository<RefreshToken, Long> {

    @Query("select token from RefreshToken token where  token.token = ?1")
    Optional<RefreshToken> findByToken(String token);

    @Modifying
    void deleteByUser(BudgetUser user);
}
