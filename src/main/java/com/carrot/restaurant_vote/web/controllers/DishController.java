package com.carrot.restaurant_vote.web.controllers;

import com.carrot.restaurant_vote.models.Dish;
import com.carrot.restaurant_vote.repositories.DishRepository;
import com.carrot.restaurant_vote.repositories.MenuRepository;
import com.carrot.restaurant_vote.web.dto.DishTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/1.0/menu/{menuId}/dish", produces = MediaType.APPLICATION_JSON_VALUE)
@Secured("ROLE_ADMIN")
public class DishController extends Controller {
    private final DishRepository dishRepository;
    private final MenuRepository menuRepository;

    public DishController(DishRepository dishRepository, MenuRepository menuRepository) {
        this.dishRepository = dishRepository;
        this.menuRepository = menuRepository;
    }

    @PostMapping
    public Dish create(@Valid @RequestBody DishTO dishTO, @PathVariable Integer menuId) {
        return dishRepository.save(
                new Dish(dishTO.getName(), dishTO.getPrice(), getOrFail(menuRepository.findById(menuId)))
        );
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Dish update(@PathVariable Integer id, @Valid @RequestBody DishTO dishTO) {
        var updateDish = getOrFail(dishRepository.findById(id));

        updateDish.setName(dishTO.getName());
        updateDish.setPrice(dishTO.getPrice());
        return dishRepository.save(updateDish);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        dishRepository.delete(getOrFail(dishRepository.findById(id)));
    }
}
