package com.gilbertcon.expensegeniespring5.converters;

import com.gilbertcon.expensegeniespring5.command.CategoryCommand;
import com.gilbertcon.expensegeniespring5.command.ExpenseCommand;
import com.gilbertcon.expensegeniespring5.model.Expense;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ExpenseCommandToExpenseTest {

    public static final Long ID_VALUE = 1L;
    public static final BigDecimal AMOUNT = new BigDecimal("1");
    public static final Date DATE = new Date();
    public static final String DESCRIPTION = "description";
    public static final Long CATEGORY_ID = 1L;

    ExpenseCommandToExpense converter;

    @BeforeEach
    void setUp() {
        converter = new ExpenseCommandToExpense(new CategoryCommandToCategory());
    }

    @Test
    public void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(new ExpenseCommand()));
    }

    @Test
    void convert() {

        ExpenseCommand expenseCommand = new ExpenseCommand();
        expenseCommand.setId(ID_VALUE);
        expenseCommand.setAmount(AMOUNT);
        expenseCommand.setDescription(DESCRIPTION);
        expenseCommand.setDate(DATE);

        CategoryCommand categoryCommand = new CategoryCommand();
        categoryCommand.setId(CATEGORY_ID);
        expenseCommand.setCategoryCommand(categoryCommand);

        Expense expense = converter.convert(expenseCommand);

        assertNotNull(expense);
        assertNotNull(expense.getCategory());
        assertEquals(ID_VALUE, expense.getId());
        assertEquals(AMOUNT, expense.getAmount());
        assertEquals(DESCRIPTION, expense.getDescription());
        assertEquals(DATE, expense.getDate());
        assertEquals(CATEGORY_ID, expense.getCategory().getId());

    }

    @Test
    public void convertWithNullCategory() {

        ExpenseCommand expenseCommand = new ExpenseCommand();
        expenseCommand.setId(ID_VALUE);
        expenseCommand.setAmount(AMOUNT);
        expenseCommand.setDescription(DESCRIPTION);
        expenseCommand.setDate(DATE);
        CategoryCommand categoryCommand = new CategoryCommand();

        Expense expense = converter.convert(expenseCommand);

        assertNotNull(expense);
        assertNull(expense.getCategory());
        assertEquals(ID_VALUE, expense.getId());
        assertEquals(AMOUNT, expense.getAmount());
        assertEquals(DESCRIPTION, expense.getDescription());
        assertEquals(DATE, expense.getDate());

    }
}