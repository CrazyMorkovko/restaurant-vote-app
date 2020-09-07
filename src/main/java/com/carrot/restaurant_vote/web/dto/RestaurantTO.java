package com.carrot.restaurant_vote.web.dto;

import com.carrot.restaurant_vote.models.Restaurant;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
public class RestaurantTO {
    private Integer id;

    @NotBlank
    @Size(min = 1, max = 100)
    private String name;

    public static RestaurantTO fromEntity(Restaurant restaurant) {
        return RestaurantTO.builder()
                .id(restaurant.getId())
                .name(restaurant.getName())
                .build();
    }
}
