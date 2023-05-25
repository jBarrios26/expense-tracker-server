package com.jorgebarrios.expensetracker.user;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.jorgebarrios.expensetracker.budget.Budget;
import com.jorgebarrios.expensetracker.category.model.BudgetCategory;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table
public class BudgetUser {
    private @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @UuidGenerator
    UUID id;
    private String username;
    private String lastName;
    private String email;
    private String password;
    private Byte sex;

    @JsonBackReference
    @OneToMany(mappedBy = "budgetUser", cascade = CascadeType.REMOVE)
    private List<Budget> budgets;

    @JsonBackReference
    @OneToMany(mappedBy = "budgetUser", cascade = CascadeType.ALL)
    private Set<BudgetCategory> budgetUserCategories;

    public BudgetUser(
            String username,
            String lastName,
            String email,
            String password,
            Byte sex
    ) {
        this.username = username;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "User{" +
               "id=" + id +
               ", username='" + username + '\'' +
               ", email='" + email + '\'' +
               ", password='" + password + '\'' +
               '}';
    }

}
