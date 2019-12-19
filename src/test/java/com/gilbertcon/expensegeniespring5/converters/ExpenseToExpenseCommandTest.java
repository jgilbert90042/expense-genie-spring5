package com.gilbertcon.expensegeniespring5.converters;

import com.gilbertcon.expensegeniespring5.command.ExpenseCommand;
import com.gilbertcon.expensegeniespring5.model.Category;
import com.gilbertcon.expensegeniespring5.model.Expense;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ExpenseToExpenseCommandTest {

    public static final Long ID_VALUE = 1L;
    public static final BigDecimal AMOUNT = new BigDecimal("1");
    public static final Date DATE = new Date();
    public static final String DESCRIPTION = "description";
    public static final Long CATEGORY_ID = 1L;

    ExpenseToExpenseCommand converter;

    @BeforeEach
    void setUp() {
        converter = new ExpenseToExpenseCommand(new CategoryToCategoryCommand());
    }

    @Test
    public void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(new Expense()));
    }

    @Test
    void convert() {
        Expense expense = new Expense();
        expense.setId(ID_VALUE);
        expense.setAmount(AMOUNT);
        expense.setDescription(DESCRIPTION);
        expense.setDate(DATE);

        Category category = new Category();
        category.setId(CATEGORY_ID);
        expense.setCategory(category);

        ExpenseCommand expenseCommand = converter.convert(expense);
        assertNotNull(expenseCommand);
        assertNotNull(expenseCommand.getCategoryCommand());
        assertEquals(ID_VALUE, expenseCommand.getId());
        assertEquals(AMOUNT, expenseCommand.getAmount());
        assertEquals(DESCRIPTION, expense.getDescription());
        assertEquals(DATE, expenseCommand.getDate());
        assertEquals(CATEGORY_ID, expenseCommand.getCategoryCommand().getId());
    }

    @Test
    void convertWithNullCategory() {
        Expense expense = new Expense();
        expense.setId(ID_VALUE);
        expense.setAmount(AMOUNT);
        expense.setDescription(DESCRIPTION);
        expense.setDate(DATE);

        Category category = new Category();

        ExpenseCommand expenseCommand = converter.convert(expense);
        assertNotNull(expenseCommand);
        assertNull(expenseCommand.getCategoryCommand());
        assertEquals(ID_VALUE, expenseCommand.getId());
        assertEquals(AMOUNT, expenseCommand.getAmount());
        assertEquals(DESCRIPTION, expense.getDescription());
        assertEquals(DATE, expenseCommand.getDate());
    }

}