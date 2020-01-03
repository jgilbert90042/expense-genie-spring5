package com.gilbertcon.expensegeniespring5.command;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class ExpenseCommand {

    private Long id;

    @NotBlank
    @Size(min = 3, max = 255)
    private String description;

    @DateTimeFormat(pattern = "MM/dd/yyy")
    @NotNull
    @PastOrPresent
    private Date date;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal amount;
    private CategoryCommand category;
}
