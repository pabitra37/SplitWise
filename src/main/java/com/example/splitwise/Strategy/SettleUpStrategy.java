package com.example.splitwise.Strategy;

import com.example.splitwise.DTOs.Transaction;
import com.example.splitwise.Models.Expense;
import org.springframework.stereotype.Component;

import java.util.List;

public interface SettleUpStrategy {
    List<Transaction> settleUp(List<Expense> expensesToSettleUp);
}
