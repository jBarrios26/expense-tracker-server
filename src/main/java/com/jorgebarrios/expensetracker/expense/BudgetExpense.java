package com.jorgebarrios.expensetracker.expense;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.jorgebarrios.expensetracker.budget.Budget;
import com.jorgebarrios.expensetracker.category.model.BudgetCategory;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;
import java.util.UUID;


@Getter
@Setter
@Entity
@Table
@NoArgsConstructor
public class BudgetExpense {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @UuidGenerator
    private UUID id;

    private Double amount;
    private String name;
    @Temporal(TemporalType.DATE)
    private Date expenseDate;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdate;

    @JsonIgnore
    @JsonManagedReference
    @ManyToOne
    @JoinColumn(
            name = "budget",
            nullable = false
    )
    private Budget budget;
    @JsonBackReference
    @ManyToOne()
    @JoinColumn(
            name = "budget_category",
            nullable = false
    )
    private BudgetCategory budgetCategory;

    public BudgetExpense(
            String name,
            Date expenseDate,
            Double amount,
            Budget budget,
            BudgetCategory budgetCategory
    ) {
        this.amount = amount;
        this.name = name;
        this.expenseDate = expenseDate;
        this.budget = budget;
        this.budgetCategory = budgetCategory;
    }
}
