package com.example.splitwise.Repositories;

import com.example.splitwise.Models.Expense;
import com.example.splitwise.Models.ExpenseUser;
import com.example.splitwise.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ExpenseUserRepository extends JpaRepository<ExpenseUser, Long> {
    List<ExpenseUser> findAllByUser(User user);
    List<ExpenseUser> findAllByExpenseIn(List<Expense> expense);
}
