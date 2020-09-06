package com.carrot.restaurant_vote;

import com.carrot.restaurant_vote.models.Dish;
import com.carrot.restaurant_vote.models.Menu;

public class DishTestData {
    public static final int NOT_FOUND = 150;

    public static Dish getNew() {
        return new Dish(null, "Shark Roll", 15.0, new Menu());
    }
}
