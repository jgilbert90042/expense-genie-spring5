package com.gilbertcon.expensegeniespring5.controllers;

import com.gilbertcon.expensegeniespring5.command.CategoryCommand;
import com.gilbertcon.expensegeniespring5.services.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@Controller
public class CategoryController {

    private static final String CATEGORY_CATEGORYFORM_URL = "category/categoryform";
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

        return CATEGORY_CATEGORYFORM_URL;
    }

    @GetMapping("/category/{categoryId}/update")
    public String updateCategory(@PathVariable Long categoryId, Model model) {

        CategoryCommand categoryCommand = categoryService.findCommandById(categoryId);

        model.addAttribute("category", categoryCommand);

        return CATEGORY_CATEGORYFORM_URL;
    }

    @PostMapping("/category")
    public String saveCategory(@Valid @ModelAttribute("category") CategoryCommand categoryCommand, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {

            bindingResult.getAllErrors().forEach(objectError -> {
                log.debug(objectError.toString());
            });

            return CATEGORY_CATEGORYFORM_URL;
        }

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
