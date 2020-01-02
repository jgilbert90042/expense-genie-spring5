package com.gilbertcon.expensegeniespring5.controllers;

import com.gilbertcon.expensegeniespring5.command.CategoryCommand;
import com.gilbertcon.expensegeniespring5.exceptions.NotFoundException;
import com.gilbertcon.expensegeniespring5.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RequiredArgsConstructor
@Controller
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    @RequestMapping("/categories")
    public String listCategories(Model model) {

        model.addAttribute("categories", categoryService.findAll());

        return "category/index";

    }

    @GetMapping
    @RequestMapping("/category/new")
    public String newCategory(Model model) {

        CategoryCommand categoryCommand = new CategoryCommand();

        model.addAttribute("category", categoryCommand);

        return "category/categoryform";
    }

    @GetMapping
    @RequestMapping("/category/{categoryId}/update")
    public String updateCategory(@PathVariable Long categoryId, Model model) {

        CategoryCommand categoryCommand = categoryService.findCommandById(categoryId);

        model.addAttribute("category", categoryCommand);

        return "category/categoryform";
    }


    @PostMapping
    @RequestMapping("/category")
    public String saveCategory(@ModelAttribute CategoryCommand categoryCommand) {
        categoryService.saveCategoryCommand(categoryCommand);
        return "redirect:/";
    }

    @GetMapping
    @RequestMapping("/category/{categoryId}/delete")
    public String deleteCategory(@PathVariable Long categoryId) {
        categoryService.deleteById(categoryId);
        return "redirect:/";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFound(){

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("404error");

        return modelAndView;
    }
}
