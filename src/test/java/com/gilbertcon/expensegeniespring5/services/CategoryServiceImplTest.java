package com.gilbertcon.expensegeniespring5.services;

import com.gilbertcon.expensegeniespring5.model.Category;
import com.gilbertcon.expensegeniespring5.repositories.CategoryRepository;
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
class CategoryServiceImplTest {

    @Mock
    CategoryRepository categoryRepository;

    @InjectMocks
    CategoryServiceImpl categoryService;

    Category returnCategory;

    @BeforeEach
    void setUp() {
        returnCategory = Category.builder().id(1L).build();
    }

    @Test
    void findAll() {
        Set<Category> returnCategoriesSet = new HashSet<>();
        returnCategoriesSet.add(Category.builder().id(1L).build());
        returnCategoriesSet.add(Category.builder().id(2L).build());

        when(categoryRepository.findAll()).thenReturn(returnCategoriesSet);

        Set<Category> categories = categoryService.findAll();

        assertNotNull(categories);
        assertEquals(2, categories.size());
    }

    @Test
    void findById() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(returnCategory));

        Category category = categoryService.findById(1L);

        assertNotNull(category);
    }

    @Test
    void findByIdNotNull() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        Category category = categoryService.findById(1L);

        assertNull(category);
    }

    @Test
    void save() {

        Category categoryToSave = Category.builder().id(1L).build();

        when(categoryRepository.save(any())).thenReturn(returnCategory);

        Category savedCategory = categoryService.save(categoryToSave);

        assertNotNull(savedCategory);

        verify(categoryRepository).save(any());
    }

    @Test
    void delete() {
        categoryService.delete(returnCategory);

        verify(categoryRepository).delete(any());
    }

    @Test
    void deleteById() {
        categoryService.deleteById(1L);
        verify(categoryRepository).deleteById(anyLong());
    }
}