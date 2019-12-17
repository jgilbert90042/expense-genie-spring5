package com.gilbertcon.expensegeniespring5.model;

import lombok.*;

import javax.persistence.Entity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Category extends BaseEntity {

    @Builder
    public Category(Long id, String description) {
        super(id);
        this.description = description;
    }

    private String description;

}
