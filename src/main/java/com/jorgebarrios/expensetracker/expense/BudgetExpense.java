package com.jorgebarrios.expensetracker.expense;

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

    @Temporal(TemporalType.DATE)
    private Date expenseDate;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdate;


    @ManyToOne
    @JoinColumn(name = "budget", nullable = false)
    private Budget budget;

    @ManyToOne
    @JoinColumn(name = "budget_category", nullable = false)
    private BudgetCategory budgetCategory;
}
