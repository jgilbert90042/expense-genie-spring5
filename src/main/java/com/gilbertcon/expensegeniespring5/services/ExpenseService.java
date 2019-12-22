package com.gilbertcon.expensegeniespring5.services;

import com.gilbertcon.expensegeniespring5.command.ExpenseCommand;
import com.gilbertcon.expensegeniespring5.model.Expense;

public interface ExpenseService extends CrudService<Expense, Long> {

    // Add in a find by month method
    public ExpenseCommand findCommandById(Long id);

    public ExpenseCommand saveExpenseCommand(ExpenseCommand command);

}
