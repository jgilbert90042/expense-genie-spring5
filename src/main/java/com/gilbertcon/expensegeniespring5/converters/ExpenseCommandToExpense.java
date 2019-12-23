package com.gilbertcon.expensegeniespring5.converters;

import com.gilbertcon.expensegeniespring5.command.ExpenseCommand;
import com.gilbertcon.expensegeniespring5.model.Expense;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExpenseCommandToExpense implements Converter<ExpenseCommand, Expense> {

    private final CategoryCommandToCategory categoryConverter;

    @Synchronized
    @Nullable
    @Override
    public Expense convert(ExpenseCommand source) {

        if (source == null) {
            return null;
        }

        Expense expense = new Expense();
        expense.setId(source.getId());
        expense.setDescription(source.getDescription());
        expense.setDate(source.getDate());
        expense.setAmount(source.getAmount());
        expense.setCategory(categoryConverter.convert(source.getCategory()));

        return expense;

    }
}
