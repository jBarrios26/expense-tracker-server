package com.jorgebarrios.expensetracker.category.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.jorgebarrios.expensetracker.budget.Budget;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table
public class BudgetCategories {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @JsonBackReference
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "budget")
    private Budget budget;


    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "budget_category")
    private BudgetCategory budgetCategory;

    private Double amountLimit;

    private Double currentSpending = 0.0;

    public BudgetCategories(
            Budget budget,
            BudgetCategory budgetCategory,
            Double amountLimit
    ) {
        this.budget = budget;
        this.budgetCategory = budgetCategory;
        this.amountLimit = amountLimit;
    }
}
