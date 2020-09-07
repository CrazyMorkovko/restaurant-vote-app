package com.carrot.restaurant_vote.web.controllers;

import com.carrot.restaurant_vote.models.Restaurant;
import com.carrot.restaurant_vote.repositories.RestaurantRepository;
import com.carrot.restaurant_vote.web.dto.RestaurantTO;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/1.0/restaurant")
public class RestaurantController extends Controller {
    private final RestaurantRepository restaurantRepository;

    public RestaurantController(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @GetMapping("{id}")
    public Restaurant findById(@PathVariable Integer id) {
        return getOrFail(restaurantRepository.findById(id));
    }

    @GetMapping
    public Iterable<Restaurant> findAll() {
        return restaurantRepository.findAll();
    }

    @PostMapping
    @Secured("ROLE_ADMIN")
    public Restaurant create(@Valid @RequestBody RestaurantTO restaurantTO, Authentication authentication) {
        return restaurantRepository.save(new Restaurant(restaurantTO.getName(), getUser(authentication)));
    }


    @PutMapping("{id}")
    @Secured("ROLE_ADMIN")
    public Restaurant update(
            @PathVariable Integer id,
            @Valid @RequestBody RestaurantTO restaurantTO,
            Authentication authentication
    ) {
        var updateRestaurant = getOrFail(restaurantRepository.findById(id));

        if (updateRestaurant.getUser().getId().equals(getUser(authentication).getId())) {
            updateRestaurant.setName(restaurantTO.getName());
            return restaurantRepository.save(updateRestaurant);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("{id}")
    @Secured("ROLE_ADMIN")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id, Authentication authentication) {
        var restaurant = getOrFail(restaurantRepository.findById(id));

        if (restaurant.getUser().getId().equals(getUser(authentication).getId())) {
            restaurantRepository.delete(restaurant);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }
}
