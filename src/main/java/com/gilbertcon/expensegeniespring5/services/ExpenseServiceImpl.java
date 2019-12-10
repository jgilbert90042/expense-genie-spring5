package com.gilbertcon.expensegeniespring5.services;

import com.gilbertcon.expensegeniespring5.model.Expense;
import com.gilbertcon.expensegeniespring5.repositories.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;

    public ExpenseServiceImpl(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @Override
    public Set<Expense> findAll() {
        Set<Expense> expenseSet = new HashSet<>();
        expenseRepository.findAll().forEach(expenseSet::add);
        return expenseSet;
    }

    @Override
    public Expense findById(Long id) {
        return expenseRepository.findById(id).orElse(null);
    }

    @Override
    public Expense save(Expense object) {
        return expenseRepository.save(object);
    }

    @Override
    public void delete(Expense object) {
        expenseRepository.delete(object);
    }

    @Override
    public void deleteById(Long id) {
        expenseRepository.deleteById(id);
    }
}
