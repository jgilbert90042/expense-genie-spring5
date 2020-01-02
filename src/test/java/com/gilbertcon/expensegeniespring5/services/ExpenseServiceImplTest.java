package com.gilbertcon.expensegeniespring5.services;

import com.gilbertcon.expensegeniespring5.command.CategoryCommand;
import com.gilbertcon.expensegeniespring5.command.ExpenseCommand;
import com.gilbertcon.expensegeniespring5.converters.CategoryCommandToCategory;
import com.gilbertcon.expensegeniespring5.converters.CategoryToCategoryCommand;
import com.gilbertcon.expensegeniespring5.converters.ExpenseCommandToExpense;
import com.gilbertcon.expensegeniespring5.converters.ExpenseToExpenseCommand;
import com.gilbertcon.expensegeniespring5.model.Category;
import com.gilbertcon.expensegeniespring5.model.Expense;
import com.gilbertcon.expensegeniespring5.repositories.ExpenseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ExpenseServiceImplTest {

    CategoryToCategoryCommand categoryToCategoryCommand = new CategoryToCategoryCommand();
    CategoryCommandToCategory categoryCommandToCategory = new CategoryCommandToCategory();

    ExpenseToExpenseCommand expenseToExpenseCommand = new ExpenseToExpenseCommand(categoryToCategoryCommand);
    ExpenseCommandToExpense expenseCommandToExpense = new ExpenseCommandToExpense(categoryCommandToCategory);


    @Mock
    ExpenseRepository expenseRepository;

    ExpenseServiceImpl expenseService;

    Expense returnExpense;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        returnExpense = Expense.builder().id(1L).build();

        expenseService =  new ExpenseServiceImpl(expenseRepository, expenseToExpenseCommand, expenseCommandToExpense);

    }

    @Test
    void findAll() {
        // given
        Set<Expense> returnExpensesSet = new HashSet<>();
        returnExpensesSet.add(Expense.builder().id(1L).build());
        returnExpensesSet.add(Expense.builder().id(2L).build());

        when(expenseRepository.findAll()).thenReturn(returnExpensesSet);

        // when
        Set<Expense> expenses = expenseService.findAll();

        // then
        assertNotNull(expenses);
        assertEquals(2, expenses.size());
    }

    @Test
    void findById() {
        // given
        when(expenseRepository.findById(anyLong())).thenReturn(Optional.of(returnExpense));

        // when
        Expense expense = expenseService.findById(1L);

        // then
        assertNotNull(expense);
    }

/*
    @Test
    void findByIdNotNull() {
        // given
        when(expenseRepository.findById(anyLong())).thenReturn(Optional.empty());

        //when
        Expense expense = expenseService.findById(1L);

        // then
        assertNull(expense);
    }
*/

    @Test
    void save() {
        // given
        Expense expenseToSave = Expense.builder().id(1L).build();

        when(expenseRepository.save(any())).thenReturn(returnExpense);

        // when
        Expense savedExpense = expenseService.save(expenseToSave);

        // then
        assertNotNull(savedExpense);
        verify(expenseRepository).save(any());
    }

    @Test
    void delete() {
        // when
        expenseService.delete(returnExpense);

        // then
        verify(expenseRepository).delete(any());
    }

    @Test
    void deleteById() {
        // when
        expenseService.deleteById(1L);

        // then
        verify(expenseRepository).deleteById(any());
    }

    @Test
    void findCommandById() {
        // given
        when(expenseRepository.findById(anyLong())).thenReturn(Optional.of(returnExpense));

        // when
        ExpenseCommand expenseCommand = expenseService.findCommandById(1L);

        // then
        assertNotNull(expenseCommand);
    }

    @Test
    void saveExpenseCommand() {

        // given
        CategoryCommand categoryCommand = categoryToCategoryCommand.convert(Category.builder().id(1L)
                .description("This is a description.").build());
        ExpenseCommand expenseCommandToSave = expenseToExpenseCommand.convert(Expense.builder().id(1L).build());

        expenseCommandToSave.setCategory(categoryCommand);

        when(expenseRepository.save(any())).thenReturn(expenseCommandToExpense.convert(expenseCommandToSave));

        // when
        ExpenseCommand savedExpenseCommand = expenseService.saveExpenseCommand(expenseCommandToSave);

        // then
        assertNotNull(savedExpenseCommand);
        assertNotNull(savedExpenseCommand.getCategory());
        verify(expenseRepository).save(any());
    }

}
