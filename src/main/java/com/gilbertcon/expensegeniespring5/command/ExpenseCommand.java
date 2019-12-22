package com.gilbertcon.expensegeniespring5.command;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class ExpenseCommand {

    private Long id;
    private String description;
    @DateTimeFormat(pattern = "MM/dd/yyy")
    private Date date;
    private BigDecimal amount;
    private CategoryCommand categoryCommand;
}
