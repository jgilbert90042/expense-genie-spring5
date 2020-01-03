package com.gilbertcon.expensegeniespring5.controllers;

import com.gilbertcon.expensegeniespring5.command.ExpenseCommand;
import com.gilbertcon.expensegeniespring5.exceptions.NotFoundException;
import com.gilbertcon.expensegeniespring5.services.CategoryService;
import com.gilbertcon.expensegeniespring5.services.ExpenseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new ControllerExceptionHandler())
                .build();
    }

    @Test
    void newExpense() throws Exception {

        //given
        ExpenseCommand expenseCommand = new ExpenseCommand();

        when(categoryService.findAllCommand()).thenReturn(new HashSet<>());

        //when - then
        mockMvc.perform(get("/expense/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("expense/expenseform"))
                .andExpect(model().attributeExists("expense"))
                .andExpect(model().attributeExists("categoryList"));

    }

    @Test
    void updateExpense() throws Exception {

        //given
        ExpenseCommand expenseCommand = new ExpenseCommand();

        when(expenseService.findCommandById(anyLong())).thenReturn(expenseCommand);
        when(categoryService.findAllCommand()).thenReturn(new HashSet<>());

        //when - then
        mockMvc.perform(get("/expense/1/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("expense/expenseform"))
                .andExpect(model().attributeExists("expense"))
                .andExpect(model().attributeExists("categoryList"));

    }

    @Test
    void updateExpenseNotFound() throws Exception {
        when(expenseService.findCommandById(anyLong())).thenThrow(NotFoundException.class);

        mockMvc.perform(get("/expense/1/update"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("404error"));

    }

    @Test
    void updateExpenseBadNumber() throws Exception {

        mockMvc.perform(get("/expense/asdcx/update"))
                .andExpect(status().isBadRequest())
                .andExpect(view().name("400error"));

    }

    @Test
    void saveOrUpdate() throws Exception {

        mockMvc.perform(post("/expense")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("description", "some string")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
    }

    @Test
    void deleteExpense() throws Exception {

        mockMvc.perform(get("/expense/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
    }
}
