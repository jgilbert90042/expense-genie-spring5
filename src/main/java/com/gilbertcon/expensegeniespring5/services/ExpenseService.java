package com.gilbertcon.expensegeniespring5.services;

import com.gilbertcon.expensegeniespring5.model.Expense;

import java.util.Set;

public interface ExpenseService {

    Set<Expense> getExpenses();

}
