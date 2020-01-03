package com.gilbertcon.expensegeniespring5.controllers;

import com.gilbertcon.expensegeniespring5.command.CategoryCommand;
import com.gilbertcon.expensegeniespring5.exceptions.NotFoundException;
import com.gilbertcon.expensegeniespring5.model.Category;
import com.gilbertcon.expensegeniespring5.services.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class CategoryControllerTest {

    @Mock
    CategoryService categoryService;

    CategoryController controller;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        controller = new CategoryController(categoryService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new ControllerExceptionHandler())
                .build();
    }

    @Test
    void listCategories() throws Exception {
        Set<Category> categories = new HashSet<>();
        categories.add(Category.builder().id(1L).build());

        when(categoryService.findAll()).thenReturn(categories);

        mockMvc.perform(get("/categories"))
                .andExpect(status().isOk())
                .andExpect(view().name("category/index"))
                .andExpect(model().attribute("categories", hasSize(1)));

        verify(categoryService).findAll();
    }

    @Test
    void newCategory() throws Exception {

        mockMvc.perform(get("/category/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("category/categoryform"))
                .andExpect(model().attributeExists("category"));
    }

    @Test
    void updateCategory() throws Exception {
        CategoryCommand category = new CategoryCommand();

        when(categoryService.findCommandById(anyLong())).thenReturn(category);

        mockMvc.perform(get("/category/1/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("category/categoryform"))
                .andExpect(model().attributeExists("category"));

    }

    @Test
    void updateCategoryNotFound() throws Exception {
        when(categoryService.findCommandById(anyLong())).thenThrow(NotFoundException.class);

        mockMvc.perform(get("/category/1/update"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("404error"));

    }

    @Test
    void updateCategoryBadNumber() throws Exception {

        mockMvc.perform(get("/category/asdcx/update"))
                .andExpect(status().isBadRequest())
                .andExpect(view().name("400error"));

    }

    @Test
    void saveOrUpdate() throws Exception {

        mockMvc.perform(post("/category")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("description", "some string")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
    }

    @Test
    void deleteExpense() throws Exception {

        mockMvc.perform(get("/category/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
    }
}
