package com.carrot.restaurant_vote.repositories;

import com.carrot.restaurant_vote.models.Menu;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

@Repository
public interface MenuRepository extends CrudRepository<Menu, Integer> {
    @EntityGraph(attributePaths = {"restaurant", "dishes"})
    Optional<Menu> findByDateAndRestaurantId(LocalDate date, Integer restaurantId);

    @EntityGraph(attributePaths = {"restaurant", "dishes"})
    Set<Menu> findAllByDate(LocalDate date);
}
