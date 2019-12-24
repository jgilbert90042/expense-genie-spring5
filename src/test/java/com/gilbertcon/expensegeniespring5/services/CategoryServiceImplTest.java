package com.gilbertcon.expensegeniespring5.services;

import com.gilbertcon.expensegeniespring5.command.CategoryCommand;
import com.gilbertcon.expensegeniespring5.converters.CategoryCommandToCategory;
import com.gilbertcon.expensegeniespring5.converters.CategoryToCategoryCommand;
import com.gilbertcon.expensegeniespring5.model.Category;
import com.gilbertcon.expensegeniespring5.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CategoryServiceImplTest {

    CategoryToCategoryCommand categoryToCategoryCommand = new CategoryToCategoryCommand();
    CategoryCommandToCategory categoryCommandToCategory = new CategoryCommandToCategory();
    CategoryService categoryService;

    @Mock
    CategoryRepository categoryRepository;

    Category returnCategory;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        returnCategory = Category.builder().id(1L).build();
        categoryService = new CategoryServiceImpl(categoryRepository, categoryToCategoryCommand, categoryCommandToCategory);
    }

    @Test
    void findAll() {

        // given
        Set<Category> returnCategoriesSet = new HashSet<>();
        returnCategoriesSet.add(Category.builder().id(1L).build());
        returnCategoriesSet.add(Category.builder().id(2L).build());

        when(categoryRepository.findAll()).thenReturn(returnCategoriesSet);

        // when
        Set<Category> categories = categoryService.findAll();

        // then
        assertNotNull(categories);
        assertEquals(2, categories.size());
    }

    @Test
    void findById() {
        // given
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(returnCategory));

        // when
        Category category = categoryService.findById(1L);

        // then
        assertNotNull(category);
    }

    @Test
    void findByIdNotNull() {

        // given
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        // when
        Category category = categoryService.findById(1L);

        // then
        assertNull(category);
    }

    @Test
    void save() {

        // given
        Category categoryToSave = Category.builder().id(1L).build();

        when(categoryRepository.save(any())).thenReturn(returnCategory);

        // when
        Category savedCategory = categoryService.save(categoryToSave);

        // then
        assertNotNull(savedCategory);
        verify(categoryRepository).save(any());
    }

    @Test
    void delete() {

        // when
        categoryService.delete(returnCategory);

        // then
        verify(categoryRepository).delete(any());
    }

    @Test
    void deleteById() {

        // when
        categoryService.deleteById(1L);

        // then
        verify(categoryRepository).deleteById(anyLong());
    }

    @Test
    void findAllCommand() {

        // Given
        Set<Category> categories = new HashSet<>();
        Category category1 = new Category();
        category1.setId(1L);
        categories.add(category1);

        Category category2 = new Category();
        category2.setId(2L);
        categories.add(category2);

        when(categoryRepository.findAll()).thenReturn(categories);

        // When
        Set<CategoryCommand> categoryCommands = categoryService.findAllCommand();

        //then
        assertEquals(2, categoryCommands.size());
        verify(categoryRepository).findAll();

    }

    @Test
    void findCommandById() {

        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(returnCategory));

        CategoryCommand categoryCommand = categoryService.findCommandById(1L);

        assertNotNull(categoryCommand);
    }

    @Test
    void saveCategoryCommand() {

        CategoryCommand categoryCommandToSave = new CategoryCommand();

        when(categoryRepository.save(any())).thenReturn(categoryCommandToCategory.convert(categoryCommandToSave));

        CategoryCommand savedCategoryCommand = categoryService.saveCategoryCommand(categoryCommandToSave);

        assertNotNull(savedCategoryCommand);
        verify(categoryRepository).save(any());

    }

}