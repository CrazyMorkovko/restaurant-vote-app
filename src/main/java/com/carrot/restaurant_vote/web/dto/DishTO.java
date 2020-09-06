package com.carrot.restaurant_vote.web.dto;

import com.carrot.restaurant_vote.models.Dish;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
public class DishTO {
    private Integer id;

    @NotBlank
    @Size(min = 1, max = 100)
    private String name;

    @NotNull
    @Range(min = 1, max = 5000)
    private Double price;

    public static DishTO fromEntity(Dish dish) {
        return DishTO.builder()
                .id(dish.getId())
                .name(dish.getName())
                .price(dish.getPrice())
                .build();
    }
}
