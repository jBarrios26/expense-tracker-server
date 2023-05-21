package com.jorgebarrios.expensetracker.category.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.jorgebarrios.expensetracker.expense.BudgetExpense;
import com.jorgebarrios.expensetracker.user.BudgetUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.Set;
import java.util.UUID;


@Getter
@Setter
@Entity
@Table
@NoArgsConstructor

public class BudgetCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @UuidGenerator
    @Column(name = "id")
    private UUID id;
    private String name;
    private String color;

    @Column(columnDefinition = "boolean default false")
    private Boolean userMade;

    @JsonIgnore
    @JsonManagedReference
    @ManyToOne(
            cascade = CascadeType.ALL,
            optional = true
    )
    private BudgetUser budgetUser;

    @JsonIgnore
    @JsonManagedReference
    @OneToMany(
            mappedBy = "budgetCategory",
            cascade = CascadeType.ALL
    )
    private Set<BudgetCategories> budgetCategories;

    @JsonIgnore
    @JsonManagedReference
    @OneToMany(
            mappedBy = "budgetCategory",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<BudgetExpense> budgetExpenses;

    public BudgetCategory(
            String name,
            String color,
            BudgetUser budgetUser
    ) {
        this.name = name;
        this.color = color;
        this.userMade = true;
        this.budgetUser = budgetUser;
    }
}
