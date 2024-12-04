package com.example.splitwise.Strategy;

import com.example.splitwise.DTOs.Transaction;
import com.example.splitwise.Models.Expense;
import com.example.splitwise.Models.ExpenseUser;
import com.example.splitwise.Models.ExpenseUserType;
import com.example.splitwise.Models.User;
import com.example.splitwise.Repositories.ExpenseUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class HeapSettleUpStrategy implements SettleUpStrategy{
    ExpenseUserRepository expenseUserRepository;

    @Autowired
    public HeapSettleUpStrategy(ExpenseUserRepository expenseUserRepository) {
        this.expenseUserRepository = expenseUserRepository;
    }
    @Override
    public List<Transaction> settleUp(List<Expense> expenses) {
        // Map to store net balances of each user
        Map<User, Integer> userBalances = new HashMap<>();
        // Fetch all ExpenseUser mappings for the given List of expenses
        List<ExpenseUser> expenseUsers = expenseUserRepository.findAllByExpenseIn(expenses);
        // Calculate balances
        for (ExpenseUser expenseUser : expenseUsers) {
            User user = expenseUser.getUser();
            int amount = expenseUser.getAmount();

            if (expenseUser.getExpenseUserType() == ExpenseUserType.PAID_BY) {
                // The user paid for the expense, reduce their balance
                userBalances.put(user, userBalances.getOrDefault(user, 0) - amount);
            } else if (expenseUser.getExpenseUserType() == ExpenseUserType.HAD_TO_PAY) {
                // The user owes for the expense, increase their balance
                userBalances.put(user, userBalances.getOrDefault(user, 0) + amount);
            }
        }
        // Min-Heap for users who owe money
        PriorityQueue<Map.Entry<User, Integer>> minHeap = new PriorityQueue<>(Comparator.comparingInt(Map.Entry::getValue));
        // Max-Heap for users who should get money
        PriorityQueue<Map.Entry<User, Integer>> maxHeap = new PriorityQueue<>((a, b) -> b.getValue() - a.getValue());
        // Separate users with positive and negative balances
        for (Map.Entry<User, Integer> entry : userBalances.entrySet()) {
            if (entry.getValue() < 0) {
                minHeap.offer(entry);
            } else if (entry.getValue() > 0) {
                maxHeap.offer(entry);
            }
        }
        // List to store resulting transactions
        List<Transaction> transactions = new ArrayList<>();
        // Settle up using min-heap and max-heap
        while (!minHeap.isEmpty() && !maxHeap.isEmpty()) {
            Map.Entry<User, Integer> debtor = minHeap.poll(); // User who owes money
            Map.Entry<User, Integer> creditor = maxHeap.poll(); // User who gets money
            // Determine the transaction amount
            assert creditor != null; //check if both are valid
            assert debtor != null;
            int settlementAmount = Math.min(-debtor.getValue(), creditor.getValue());
            // Create a transaction
            Transaction transaction = new Transaction();
            transaction.setPayer(debtor.getKey());
            transaction.setReceiver(creditor.getKey());
            transaction.setAmount(settlementAmount);
            transactions.add(transaction);
            // Update balances
            debtor.setValue(debtor.getValue() + settlementAmount);
            creditor.setValue(creditor.getValue() - settlementAmount);
            // Re-add to heaps if balances are not settled
            if (debtor.getValue() < 0) minHeap.offer(debtor);
            if (creditor.getValue() > 0) maxHeap.offer(creditor);
        }
        return transactions;
    }

}
