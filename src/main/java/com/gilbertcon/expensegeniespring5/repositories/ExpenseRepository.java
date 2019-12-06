package com.gilbertcon.expensegeniespring5.repositories;

import com.gilbertcon.expensegeniespring5.model.Expense;
import org.springframework.data.repository.CrudRepository;

public interface ExpenseRepository extends CrudRepository<Expense, Long> {
}
