package com.gilbertcon.expensegeniespring5.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Expense  extends BaseEntity {

    @Builder
    public Expense(Long id, Date date, String description, BigDecimal amount) {
        super(id);
        this.date = date;
        this.description = description;
        this.amount = amount;
    }

    @Temporal(TemporalType.DATE)
    private Date date;
    private String description;
    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

}
