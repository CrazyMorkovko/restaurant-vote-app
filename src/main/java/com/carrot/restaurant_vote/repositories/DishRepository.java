package com.carrot.restaurant_vote.repositories;

import com.carrot.restaurant_vote.models.Dish;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DishRepository extends CrudRepository<Dish, Integer> {

}
