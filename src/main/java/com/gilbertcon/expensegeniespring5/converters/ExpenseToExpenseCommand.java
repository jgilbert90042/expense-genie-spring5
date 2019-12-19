package com.gilbertcon.expensegeniespring5.converters;

import com.gilbertcon.expensegeniespring5.command.ExpenseCommand;
import com.gilbertcon.expensegeniespring5.model.Expense;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ExpenseToExpenseCommand implements Converter<Expense, ExpenseCommand> {

    private final CategoryToCategoryCommand categoryConverter;

    @Synchronized
    @Nullable
    @Override
    public ExpenseCommand convert(Expense source) {

        if (source == null) {
            return null;
        }

        ExpenseCommand expenseCommand = new ExpenseCommand();
        expenseCommand.setId(source.getId());
        expenseCommand.setDescription(source.getDescription());
        expenseCommand.setDate(source.getDate());
        expenseCommand.setAmount(source.getAmount());
        expenseCommand.setCategoryCommand(categoryConverter.convert(source.getCategory()));

        return expenseCommand;
    }
}
