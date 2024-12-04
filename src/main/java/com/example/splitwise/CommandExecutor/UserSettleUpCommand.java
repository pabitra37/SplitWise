package com.example.splitwise.CommandExecutor;

import com.example.splitwise.Controllers.SettleUpController;
import com.example.splitwise.DTOs.SettleUpUserRequestDTO;
import com.example.splitwise.DTOs.SettleUpUserResponseDTO;
import com.example.splitwise.DTOs.Transaction;
import com.example.splitwise.Models.Expense;
import com.example.splitwise.Models.ExpenseType;
import com.example.splitwise.Models.ExpenseUser;
import com.example.splitwise.Models.ExpenseUserType;
import com.example.splitwise.Repositories.ExpenseRepository;
import com.example.splitwise.Repositories.ExpenseUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

@Component
public class UserSettleUpCommand implements Command{
    SettleUpController settleUpController;
    ExpenseRepository expenseRepository;
    ExpenseUserRepository expenseUserRepository;
    @Autowired
    public UserSettleUpCommand(SettleUpController settleUpController, ExpenseUserRepository expenseUserRepository,
                               ExpenseRepository expenseRepository) {
        this.settleUpController = settleUpController;
        this.expenseUserRepository = expenseUserRepository;
        this.expenseRepository = expenseRepository;
    }

    @Override
    public boolean matches(String input) {
        String[] words = input.split(" ");
        return words.length == 2 && words[1].equalsIgnoreCase("SettleUp");
    }

    @Override
    public void execute(String input) {
        Long userID = Long.parseLong(input.split(" ")[0]);
        SettleUpUserRequestDTO settleUpUserRequestDTO = new SettleUpUserRequestDTO();
        settleUpUserRequestDTO.setUserId(userID);
        SettleUpUserResponseDTO settleUpUserResponseDTO = settleUpController.settleUpUser(
                                                                    settleUpUserRequestDTO);
        System.out.println(settleUpUserResponseDTO.getStatus());
        // Initialize a list to keep track of transactions that are not settled
        List<Transaction> unsettledTransactions = new ArrayList<>();
        //check if user wants to settleUp a transaction
        Scanner scanner = new Scanner(System.in);
        for (Transaction transaction : settleUpUserResponseDTO.getTransactions()) {
            System.out.println("Transaction: " + transaction.getPayer().getName() + " pays " +
                    transaction.getAmount() + " to " + transaction.getReceiver().getName());
            System.out.print("Do you want to mark this transaction as settled? (yes/no): ");
            String userInput = scanner.nextLine().trim();

            if ("yes".equalsIgnoreCase(userInput)) {
                // Create a dummy expense to cancel out this transaction
                createDummyExpense(transaction);
            }else {
                // Add the transaction to the list of unsettled transactions
                unsettledTransactions.add(transaction);
            }
        }
    }
    private void createDummyExpense(Transaction transaction) {
        // Calculate the settle-up amount, which is the amount the user is owed or should pay
        int settleUpAmount = Math.abs(transaction.getAmount());
        // Create a dummy expense to cancel out the real transaction
        Expense dummyExpense = new Expense();
        dummyExpense.setDescription("Dummy expense to settle up transaction");
        dummyExpense.setAmount(settleUpAmount); // Settle the amount, which is positive
        dummyExpense.setExpenseType(ExpenseType.DUMMY); // Mark as a dummy expense
        // Create corresponding ExpenseUser entries for the dummy expense
        // User A is receiving money (A is "owed" money)
        ExpenseUser expenseUserA = new ExpenseUser();
        expenseUserA.setExpense(dummyExpense);
        //A was the one who paid in the original transaction, now they are "owed" money
        expenseUserA.setUser(transaction.getPayer());
        //A will receive the amount, so we use a -ve value to show that A is "owed" money in the dummy transaction
        expenseUserA.setAmount(-settleUpAmount);
        // A is the one who "should receive" the money
        expenseUserA.setExpenseUserType(ExpenseUserType.HAD_TO_PAY);
        // User B is paying money (B is the one who "owes" money)
        ExpenseUser expenseUserB = new ExpenseUser();
        expenseUserB.setExpense(dummyExpense);
        // B was the one who owed money in the original transaction, now B is paying
        expenseUserB.setUser(transaction.getReceiver());
        // B pays the amount, so we use a positive value to show that B is "paying" money
        expenseUserB.setAmount(settleUpAmount);
        expenseUserB.setExpenseUserType(ExpenseUserType.PAID_BY); // B is the one who "paid" the money

        // Save the dummy expense and related ExpenseUser entries
        expenseRepository.save(dummyExpense);  // Save the dummy expense to the repository
        expenseUserRepository.save(expenseUserA);  // Save the expense user record for User A
        expenseUserRepository.save(expenseUserB);  // Save the expense user record for User B
    }
}
