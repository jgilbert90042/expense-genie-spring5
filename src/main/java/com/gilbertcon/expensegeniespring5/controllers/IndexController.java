package com.gilbertcon.expensegeniespring5.controllers;

import com.gilbertcon.expensegeniespring5.services.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final ExpenseService expenseService;

    @GetMapping({"/", "index", "index.html"})
    public String getIndexPage(Model model) {

        model.addAttribute("expenses", expenseService.findAll());

        return "index";
    }

}
