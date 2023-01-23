package com.jorgebarrios.expensetracker.auth.service;

import com.jorgebarrios.expensetracker.auth.exception.TokenRefreshException;
import com.jorgebarrios.expensetracker.auth.model.RefreshToken;
import com.jorgebarrios.expensetracker.auth.repository.AuthRepository;
import com.jorgebarrios.expensetracker.common.exception.BudgetUserNotFoundException;
import com.jorgebarrios.expensetracker.security.JwtUtils;
import com.jorgebarrios.expensetracker.user.BudgetUser;
import com.jorgebarrios.expensetracker.user.BudgetUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthRepository authRepository;
    private final BudgetUserRepository budgetUserRepository;
    private final JwtUtils jwtUtils;
    private final UserDetailsService userDetailsService;

    public Optional<RefreshToken> findByToken(String token) {
        return authRepository.findByToken(token);
    }

    @Transactional
    public RefreshToken createRefreshToken(final String userId) {
        RefreshToken refreshToken = new RefreshToken();
        Optional<BudgetUser> user =
                budgetUserRepository.findById(UUID.fromString(userId));
        if (user.isEmpty()) {
            throw new BudgetUserNotFoundException(userId);
        }

        deleteByUserId(userId);

        final UserDetails userDetails =
                userDetailsService.loadUserByUsername(user.get()
                                                          .getEmail());
        final String token = jwtUtils.generateRefreshToken(userDetails);
        refreshToken.setUser(user.get());
        refreshToken.setToken(token);
        refreshToken.setExpiryDate(jwtUtils.extractExpirationDate(token));

        refreshToken = authRepository.save(refreshToken);

        return refreshToken;
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate()
                 .compareTo(new Date(System.currentTimeMillis())) < 0) {
            authRepository.delete(token);
            throw new TokenRefreshException(
                    token.getToken(),
                    "Refresh token was expired. Please make a new signin request"
            );
        }

        return token;
    }

    @Transactional
    public void deleteByUserId(final String userId) {
        Optional<BudgetUser> user =
                budgetUserRepository.findById(UUID.fromString(userId));
        if (user.isEmpty()) {
            throw new BudgetUserNotFoundException(userId);
        }
        authRepository.deleteByUser(user.get());
    }
}
