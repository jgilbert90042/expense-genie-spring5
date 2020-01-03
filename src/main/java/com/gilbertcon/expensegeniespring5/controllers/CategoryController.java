package com.gilbertcon.expensegeniespring5.controllers;

import com.gilbertcon.expensegeniespring5.command.CategoryCommand;
import com.gilbertcon.expensegeniespring5.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String updateCategory(@PathVariable Long categoryId, Model model) {

        CategoryCommand categoryCommand = categoryService.findCommandById(categoryId);

        model.addAttribute("category", categoryCommand);

        return "category/categoryform";
    }


    @PostMapping("/category")
    public String saveCategory(@ModelAttribute CategoryCommand categoryCommand) {
        categoryService.saveCategoryCommand(categoryCommand);
        return "redirect:/";
    }

    @GetMapping
    @RequestMapping("/category/{categoryId}/delete")
    public String deleteCategory(@PathVariable String categoryId) {
        categoryService.deleteById(Long.valueOf(categoryId));
        return "redirect:/";
    }



}
