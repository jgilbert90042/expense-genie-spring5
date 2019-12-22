package com.gilbertcon.expensegeniespring5.controllers;

import com.gilbertcon.expensegeniespring5.model.Expense;
import com.gilbertcon.expensegeniespring5.services.CategoryService;
import com.gilbertcon.expensegeniespring5.services.ExpenseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class ExpenseControllerTest {

    @Mock
    ExpenseService expenseService;

    @Mock
    CategoryService categoryService;

    ExpenseController controller;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        controller = new ExpenseController(expenseService, categoryService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void updateExpense() {
        Expense expense
    }

    @Test
    void saveOrUpdate() {
    }
}
