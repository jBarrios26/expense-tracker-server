package com.jorgebarrios.expensetracker.budget;

import com.jorgebarrios.expensetracker.category.model.BudgetCategories;
import com.jorgebarrios.expensetracker.expense.BudgetExpense;
import com.jorgebarrios.expensetracker.user.BudgetUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;
import java.util.Set;
import java.util.UUID;


@Getter
@Setter
@Entity
@Table
@NoArgsConstructor
@ToString()
public class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @UuidGenerator
    private UUID id;
    private String name;
    private String description;
    private Double budgetAmountLimit;
    private Double totalSpending = 0.0;

    @Temporal(TemporalType.DATE)
    private Date budgetPeriod;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdate;

    @ManyToOne(
            fetch = FetchType.EAGER
    )
    @JoinColumn(
            name = "budget_user",
            nullable = false
    )
    private BudgetUser budgetUser;

    @OneToMany(
            mappedBy = "budget",
            fetch = FetchType.EAGER,
            cascade =
                    CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<BudgetExpense> budgetExpenses;

    @OneToMany(
            mappedBy = "budget",
            fetch = FetchType.EAGER,
            cascade =
                    CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<BudgetCategories> budgetCategories;

    public Budget(
            String name,
            String description,
            Double budgetAmountLimit,
            Date budgetPeriod,
            BudgetUser budgetUser
    ) {
        this.name = name;
        this.description = description;
        this.budgetAmountLimit = budgetAmountLimit;
        this.budgetPeriod = budgetPeriod;
        this.budgetUser = budgetUser;
    }
}
