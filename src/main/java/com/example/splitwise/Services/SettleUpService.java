package com.example.splitwise.Services;

import com.example.splitwise.DTOs.Transaction;
import com.example.splitwise.Exceptions.InvalidGroupException;
import com.example.splitwise.Exceptions.InvalidUserException;
import com.example.splitwise.Models.Expense;
import com.example.splitwise.Models.ExpenseUser;
import com.example.splitwise.Models.Group;
import com.example.splitwise.Models.User;
import com.example.splitwise.Repositories.ExpenseRepository;
import com.example.splitwise.Repositories.ExpenseUserRepository;
import com.example.splitwise.Repositories.GroupRepository;
import com.example.splitwise.Repositories.UserRepository;
import com.example.splitwise.Strategy.SettleUpStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SettleUpService {
    private final UserRepository userRepository;
    private final ExpenseUserRepository expenseUserRepository;
    private final SettleUpStrategy settleUpStrategy;
    private final GroupRepository groupRepository;
    @Autowired
    public SettleUpService(UserRepository userRepository, ExpenseUserRepository expenseUserRepository, SettleUpStrategy settleUpStrategy, GroupRepository groupRepository) {
        this.userRepository = userRepository;
        this.expenseUserRepository = expenseUserRepository;
        this.settleUpStrategy = settleUpStrategy;
        this.groupRepository = groupRepository;
    }

    public List<Transaction> SettleUpUser(Long UserId) throws InvalidUserException {
        /*1. Get all the expenses which user is part of
        * 2. Iterate through all the expenses & find out who owes/gets-back how much
        * 3. use Min and Max Heap approach to find and minimise all the transactions
        * 4. Return all transactions of the user*/

        Optional<User> userOptional = userRepository.findById(UserId);
        if(userOptional.isEmpty()){
            throw new InvalidUserException();
        }
        User user = userOptional.get();
        List<ExpenseUser> expenseUsers = expenseUserRepository.findAllByUser(user);
        List<Expense> expensesToSettleUp = new ArrayList<>();
        for(ExpenseUser expenseUser : expenseUsers){
            expensesToSettleUp.add(expenseUser.getExpense());
        }
        List<Transaction> transactions = settleUpStrategy.settleUp(expensesToSettleUp);
        // 3. Filter transactions involving the current user
        return transactions.stream()
                .filter(t -> t.getPayer().equals(user) || t.getReceiver().equals(user))
                .collect(Collectors.toList());
    }

    public List<Transaction> SettleUpGroup(Long groupId) throws InvalidGroupException {
         /*1. Get all the expenses for the provided Group
         * 2. Iterate through all the expenses & find out who owes/gets-back how much
         * 3. use Min and Max Heap approach to find and minimise all the transactions
         * 4. Return all transactions for all the users*/
        Optional<Group> groupOptional = groupRepository.findById(groupId);
        if(groupOptional.isEmpty()){
            throw new InvalidGroupException();
        }
        Group group = groupOptional.get();
        return settleUpStrategy.settleUp(group.getExpenses());
    }
}
