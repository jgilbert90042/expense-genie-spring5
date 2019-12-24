package com.gilbertcon.expensegeniespring5.services;

import com.gilbertcon.expensegeniespring5.command.CategoryCommand;
import com.gilbertcon.expensegeniespring5.model.Category;

import java.util.Set;

public interface CategoryService extends CrudService<Category, Long> {

    public Set<CategoryCommand> findAllCommand();

    public CategoryCommand findCommandById(Long id);

    public CategoryCommand saveCategoryCommand(CategoryCommand categoryCommand);

}
