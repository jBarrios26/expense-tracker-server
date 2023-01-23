package com.jorgebarrios.expensetracker.security;

import com.jorgebarrios.expensetracker.user.BudgetUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;

@Configuration
@RequiredArgsConstructor
public class UserDetailsConfig {
    private final BudgetUserRepository repository;

    @Bean
    public UserDetailsService userDetailsService() {
        return email -> repository
                .findBudgetUserByEmail(email)
                .stream()
                .map(
                        (user) -> new User(
                                user.getEmail(),
                                user.getPassword(),
                                Collections.emptyList()
                        )
                    )
                .findFirst()
                .orElseThrow(
                        () -> new UsernameNotFoundException(
                                "Username with email " + email +
                                " doesn't exists"
                        )
                            );
    }
}
