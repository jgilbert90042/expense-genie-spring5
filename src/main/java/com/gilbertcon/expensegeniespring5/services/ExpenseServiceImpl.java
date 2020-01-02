package com.gilbertcon.expensegeniespring5.services;

import com.gilbertcon.expensegeniespring5.command.ExpenseCommand;
import com.gilbertcon.expensegeniespring5.converters.ExpenseCommandToExpense;
import com.gilbertcon.expensegeniespring5.converters.ExpenseToExpenseCommand;
import com.gilbertcon.expensegeniespring5.exceptions.NotFoundException;
import com.gilbertcon.expensegeniespring5.model.Expense;
import com.gilbertcon.expensegeniespring5.repositories.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final ExpenseToExpenseCommand expenseToExpenseCommand;
    private final ExpenseCommandToExpense expenseCommandToExpense;

    @Override
    public Set<Expense> findAll() {
        Set<Expense> expenseSet = new HashSet<>();
        expenseRepository.findAll().forEach(expenseSet::add);
        return expenseSet;
    }

    @Override
    public Expense findById(Long id) {

        Optional<Expense> expenseOptional = expenseRepository.findById(id);

        if (!expenseOptional.isPresent()) {
            throw new NotFoundException("Expense Not Found for ID" + id.toString());
        }

        return expenseOptional.get();
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

    @Override
    public ExpenseCommand findCommandById(Long id) {
        return expenseToExpenseCommand.convert(findById(id));
    }

    @Override
    public ExpenseCommand saveExpenseCommand(ExpenseCommand command) {

        Expense expense = expenseRepository.save(expenseCommandToExpense.convert(command));

        return expenseToExpenseCommand.convert(expense);
    }
}
