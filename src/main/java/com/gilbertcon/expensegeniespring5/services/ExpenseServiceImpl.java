package com.gilbertcon.expensegeniespring5.services;

import com.gilbertcon.expensegeniespring5.model.Expense;
import com.gilbertcon.expensegeniespring5.repositories.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private ExpenseRepository expenseRepository;

    public ExpenseServiceImpl(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @Override
    public Set<Expense> getExpenses() {
        Set<Expense> expenseSet = new HashSet<>();
        expenseRepository.findAll().iterator().forEachRemaining(expenseSet::add);
        return expenseSet;
    }
}
