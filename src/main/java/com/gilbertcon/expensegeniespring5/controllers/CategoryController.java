package com.gilbertcon.expensegeniespring5.controllers;

import com.gilbertcon.expensegeniespring5.command.CategoryCommand;
import com.gilbertcon.expensegeniespring5.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/categories")
    public String listCategories(Model model) {

        model.addAttribute("categories", categoryService.findAll());

        return "category/index";

    }

    @GetMapping("/category/new")
    public String newCategory(Model model) {

        CategoryCommand categoryCommand = new CategoryCommand();

        model.addAttribute("category", categoryCommand);

        return "category/categoryform";
    }

    @GetMapping("/category/{categoryId}/update")
    public String updateCategory(@PathVariable String categoryId, Model model) {

        CategoryCommand categoryCommand = categoryService.findCommandById(Long.valueOf(categoryId));

        model.addAttribute("category", categoryCommand);

        return "category/categoryform";
    }


    @PostMapping("/category")
    public String saveCategory(@ModelAttribute CategoryCommand categoryCommand) {
        categoryService.saveCategoryCommand(categoryCommand);
        return "redirect:/";
    }

    @GetMapping("/category/{categoryId}/delete")
    public String deleteCategory(@PathVariable String categoryId) {
        categoryService.deleteById(Long.valueOf(categoryId));
        return "redirect:/";
    }
}
