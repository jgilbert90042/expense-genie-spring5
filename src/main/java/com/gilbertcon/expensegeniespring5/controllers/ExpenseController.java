package com.gilbertcon.expensegeniespring5.controllers;

import com.gilbertcon.expensegeniespring5.command.CategoryCommand;
import com.gilbertcon.expensegeniespring5.command.ExpenseCommand;
import com.gilbertcon.expensegeniespring5.services.CategoryService;
import com.gilbertcon.expensegeniespring5.services.ExpenseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ExpenseController {

    private static final String EXPENSE_EXPENSEFORM_URL = "expense/expenseform";
    private final ExpenseService expenseService;
    private final CategoryService categoryService;

    @GetMapping("/expense/new")
    public String newExpense(Model model) {

        ExpenseCommand expenseCommand = new ExpenseCommand();
        expenseCommand.setCategory(new CategoryCommand());
        model.addAttribute("expense", expenseCommand);

        model.addAttribute("categoryList", categoryService.findAllCommand());

        return EXPENSE_EXPENSEFORM_URL;

    }

    @GetMapping("/expense/{expenseId}/update")
    public String updateExpense(@PathVariable String expenseId, Model model) {
        model.addAttribute("expense", expenseService.findCommandById(Long.valueOf(expenseId)));
        model.addAttribute("categoryList", categoryService.findAllCommand());
        return EXPENSE_EXPENSEFORM_URL;
    }

    @PostMapping("/expense")
    public String saveOrUpdate(@Valid @ModelAttribute("expense") ExpenseCommand command, BindingResult bindingResult, Model model) {

        if(bindingResult.hasErrors()) {

            bindingResult.getAllErrors().forEach(objectError -> {
                log.debug(objectError.toString());
            });

            model.addAttribute("categoryList", categoryService.findAllCommand());

            return EXPENSE_EXPENSEFORM_URL;
        }

        expenseService.saveExpenseCommand(command);
        return "redirect:/";
    }

    @GetMapping("/expense/{expenseId}/delete")
    public String deleteExpense(@PathVariable String expenseId) {
        expenseService.deleteById(Long.valueOf(expenseId));
        return "redirect:/";
    }

}
