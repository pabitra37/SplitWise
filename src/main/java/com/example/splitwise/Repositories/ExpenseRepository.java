package com.example.splitwise.Repositories;

import com.example.splitwise.Models.Expense;
import com.example.splitwise.Models.Group;
import com.example.splitwise.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findAllByGroup(Group group);
}
