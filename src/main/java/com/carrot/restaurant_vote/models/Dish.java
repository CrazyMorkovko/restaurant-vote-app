package com.carrot.restaurant_vote.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "dishes", uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "menu_id"})})
public class Dish extends NamedEntity {
    @NotNull
    @Range(min = 1, max = 5000)
    @Column(name = "price", nullable = false)
    private Double price;

    @NotNull
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Menu menu;

    public Dish(String name, Double price, Menu menu) {
        this(null, name, price, menu);
    }

    public Dish(Integer id, String name, Double price, Menu menu) {
        super(id, name);

        this.price = price;
        this.menu = menu;
    }
}
