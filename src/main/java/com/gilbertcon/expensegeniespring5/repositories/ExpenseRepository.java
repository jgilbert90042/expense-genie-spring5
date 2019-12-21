package com.gilbertcon.expensegeniespring5.repositories;

import com.gilbertcon.expensegeniespring5.model.Expense;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.Set;

public interface ExpenseRepository extends CrudRepository<Expense, Long> {

    // https://www.baeldung.com/spring-data-jpa-query-by-date
    Set<Expense> findAllByDateBetween(Date dateStart, Date dateEnd);
}
