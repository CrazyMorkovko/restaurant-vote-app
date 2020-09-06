package com.carrot.restaurant_vote.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "menus", uniqueConstraints = @UniqueConstraint(columnNames = {"date", "restaurant_id"}))
public class Menu extends BaseEntity {
    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @NotNull
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @NotNull
    @JsonIgnore
    @JoinColumn(name = "menu_id")
    @OneToMany(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Dish> dishes = new HashSet<>();

    public Menu(LocalDate date, Restaurant restaurant) {
        this(null, date, restaurant);
    }

    public Menu(Integer id, LocalDate date, Restaurant restaurant) {
        super(id);

        this.date = date;
        this.restaurant = restaurant;
    }
}
