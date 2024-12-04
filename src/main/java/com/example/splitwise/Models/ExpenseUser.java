package com.example.splitwise.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;

@Entity(name = "ExpenseUsers") //always name table in PLURALS
public class ExpenseUser extends BaseModel {
//As it's a mapping class, relation is always M:1
    @ManyToOne
    private Expense expense;
    @ManyToOne
    private User user;
    private int amount;
    @Enumerated(EnumType.ORDINAL)
    protected ExpenseUserType expenseUserType;
    public Expense getExpense() {
        return expense;
    }

    public void setExpense(Expense expense) {
        this.expense = expense;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public ExpenseUserType getExpenseUserType() {
        return expenseUserType;
    }

    public void setExpenseUserType(ExpenseUserType expenseUserType) {
        this.expenseUserType = expenseUserType;
    }


}
