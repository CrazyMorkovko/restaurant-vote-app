package com.carrot.restaurant_vote.web.dto;

import com.carrot.restaurant_vote.models.Menu;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
public class RestaurantMenuWithDish {
    private Integer id;
    private LocalDate date;
    private RestaurantTO restaurant;
    private Set<DishTO> dishes;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer votes;

    public static RestaurantMenuWithDish fromEntity(Menu menu) {
        return RestaurantMenuWithDish.builder()
                .id(menu.getId())
                .date(menu.getDate())
                .restaurant(RestaurantTO.fromEntity(menu.getRestaurant()))
                .dishes(menu.getDishes().stream().map(DishTO::fromEntity).collect(Collectors.toSet()))
                .build();
    }
}
