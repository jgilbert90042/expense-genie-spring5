package com.gilbertcon.expensegeniespring5.services;

import com.gilbertcon.expensegeniespring5.model.Expense;
import com.gilbertcon.expensegeniespring5.repositories.ExpenseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExpenseServiceImplTest {

    @Mock
    ExpenseRepository expenseRepository;

    @InjectMocks
    ExpenseServiceImpl expenseService;

    Expense returnExpense;
    @BeforeEach
    void setUp() {
        returnExpense = Expense.builder().id(1L).build();
    }

    @Test
    void findAll() {
        Set<Expense> returnExpensesSet = new HashSet<>();
        returnExpensesSet.add(Expense.builder().id(1L).build());
        returnExpensesSet.add(Expense.builder().id(2L).build());

        when(expenseRepository.findAll()).thenReturn(returnExpensesSet);

        Set<Expense> expenses = expenseService.findAll();

        assertNotNull(expenses);
        assertEquals(2, expenses.size());
    }

    @Test
    void findById() {
        when(expenseRepository.findById(anyLong())).thenReturn(Optional.of(returnExpense));

        Expense expense = expenseService.findById(1L);

        assertNotNull(expense);
    }

    @Test
    void findByIdNotNull() {
        when(expenseRepository.findById(anyLong())).thenReturn(Optional.empty());

        Expense expense = expenseService.findById(1L);

        assertNull(expense);
    }

    @Test
    void save() {
        Expense expenseToSave = Expense.builder().id(1L).build();

        when(expenseRepository.save(any())).thenReturn(returnExpense);

        Expense savedExpense = expenseService.save(expenseToSave);

        assertNotNull(savedExpense);

        verify(expenseRepository).save(any());
    }

    @Test
    void delete() {
        expenseService.delete(returnExpense);

        verify(expenseRepository).delete(any());
    }

    @Test
    void deleteById() {
        expenseService.deleteById(1L);

        verify(expenseRepository).deleteById(any());
    }
}