package com.carrot.restaurant_vote.repositories;

import com.carrot.restaurant_vote.models.Restaurant;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends CrudRepository<Restaurant, Integer> {

}
