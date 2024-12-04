package com.example.splitwise.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;

import java.util.Date;

@Entity(name = "Expenses") //always name table in PLURALS
public class Expense extends BaseModel{
    private String description;
    private int amount;
    @ManyToOne
    private User createdBy;
    @Enumerated(EnumType.ORDINAL)
    private ExpenseType expenseType;
    @ManyToOne
    private Group group;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getCreatorBy() {
        return createdBy;
    }

    public void setCreatorBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public ExpenseType getExpenseType() {
        return expenseType;
    }

    public void setExpenseType(ExpenseType expenseType) {
        this.expenseType = expenseType;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
