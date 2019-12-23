package com.gilbertcon.expensegeniespring5.controllers;

import com.gilbertcon.expensegeniespring5.command.ExpenseCommand;
import com.gilbertcon.expensegeniespring5.services.CategoryService;
import com.gilbertcon.expensegeniespring5.services.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
public class ExpenseController {

    private final ExpenseService expenseService;
    private final CategoryService categoryService;

    @GetMapping
    @RequestMapping("/expense/{expenseId}/update")
    public String updateExpense(@PathVariable String expenseId, Model model) {
        model.addAttribute("expense", expenseService.findCommandById(Long.valueOf(expenseId)));
        model.addAttribute("categoryList", categoryService.findAllCommand());
        return "expense/expenseform";
    }

    @PostMapping
    @RequestMapping("/expense")
    public String saveOrUpdate(@ModelAttribute ExpenseCommand command) {
        expenseService.saveExpenseCommand(command);
        return "redirect:/";
    }

}
