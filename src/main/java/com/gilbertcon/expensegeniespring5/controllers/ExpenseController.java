package com.gilbertcon.expensegeniespring5.controllers;

import com.gilbertcon.expensegeniespring5.command.CategoryCommand;
import com.gilbertcon.expensegeniespring5.command.ExpenseCommand;
import com.gilbertcon.expensegeniespring5.services.CategoryService;
import com.gilbertcon.expensegeniespring5.services.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class ExpenseController {

    private final ExpenseService expenseService;
    private final CategoryService categoryService;

    @GetMapping("/expense/new")
    public String newExpense(Model model) {

        ExpenseCommand expenseCommand = new ExpenseCommand();
        expenseCommand.setCategory(new CategoryCommand());
        model.addAttribute("expense", expenseCommand);

        model.addAttribute("categoryList", categoryService.findAllCommand());

        return "expense/expenseform";

    }

    @GetMapping("/expense/{expenseId}/update")
    public String updateExpense(@PathVariable String expenseId, Model model) {
        model.addAttribute("expense", expenseService.findCommandById(Long.valueOf(expenseId)));
        model.addAttribute("categoryList", categoryService.findAllCommand());
        return "expense/expenseform";
    }

    @PostMapping("/expense")
    public String saveOrUpdate(@ModelAttribute ExpenseCommand command) {
        expenseService.saveExpenseCommand(command);
        return "redirect:/";
    }

    @GetMapping("/expense/{expenseId}/delete")
    public String deleteExpense(@PathVariable String expenseId) {
        expenseService.deleteById(Long.valueOf(expenseId));
        return "redirect:/";
    }

}
