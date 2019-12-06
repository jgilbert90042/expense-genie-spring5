package com.gilbertcon.expensegeniespring5.repositories;

import com.gilbertcon.expensegeniespring5.model.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {
}
