package com.gilbertcon.expensegeniespring5.services;

import com.gilbertcon.expensegeniespring5.command.CategoryCommand;
import com.gilbertcon.expensegeniespring5.converters.CategoryCommandToCategory;
import com.gilbertcon.expensegeniespring5.converters.CategoryToCategoryCommand;
import com.gilbertcon.expensegeniespring5.exceptions.NotFoundException;
import com.gilbertcon.expensegeniespring5.model.Category;
import com.gilbertcon.expensegeniespring5.repositories.CategoryRepository;
import com.gilbertcon.expensegeniespring5.repositories.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ExpenseRepository expenseRepository;
    private final CategoryToCategoryCommand categoryToCategoryCommand;
    private final CategoryCommandToCategory categoryCommandToCategory;

    @Override
    public Set<Category> findAll() {
        Set<Category> categorySet = new HashSet<>();
        categoryRepository.findAll().forEach(categorySet::add);
        return categorySet;
    }

    @Override
    public Category findById(Long id) {

        Optional<Category> categoryOptional = categoryRepository.findById(id);

        if (!categoryOptional.isPresent()) {
            throw new NotFoundException("Category Not Found");
        }

        return categoryOptional.get();
    }

    @Override
    public Category save(Category object) {
        return categoryRepository.save(object);
    }

    @Override
    public void delete(Category category) {

        expenseRepository.findAll().iterator().forEachRemaining(expense -> {
            if (expense.getCategory() == category) {
                expense.setCategory(null);
                expenseRepository.save(expense);
            }
        });

        categoryRepository.delete(category);
    }

    @Override
    public void deleteById(Long id) {

        Optional<Category> categoryOptional = categoryRepository.findById(id);

        if (!categoryOptional.isPresent()) {
            return;
        }

        final Category category = categoryOptional.get();

        expenseRepository.findAll().iterator().forEachRemaining(expense -> {
            if (expense.getCategory() == category) {
                expense.setCategory(null);
                expenseRepository.save(expense);
            }
        });

        categoryRepository.delete(category);
    }

    @Override
    public Set<CategoryCommand> findAllCommand() {
        return StreamSupport.stream(categoryRepository.findAll()
                .spliterator(), false)
                .map(categoryToCategoryCommand::convert)
                .collect(Collectors.toSet());
    }

    @Override
    public CategoryCommand findCommandById(Long id) {
        return categoryToCategoryCommand.convert(findById(id));
    }

    @Override
    public CategoryCommand saveCategoryCommand(CategoryCommand categoryCommand) {
        Category category = categoryRepository.save(categoryCommandToCategory.convert(categoryCommand));

        return categoryToCategoryCommand.convert(category);
    }

}
