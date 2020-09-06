package com.carrot.restaurant_vote.web.controllers;

import com.carrot.restaurant_vote.models.Menu;
import com.carrot.restaurant_vote.models.VoteCount;
import com.carrot.restaurant_vote.repositories.MenuRepository;
import com.carrot.restaurant_vote.repositories.RestaurantRepository;
import com.carrot.restaurant_vote.repositories.VoteRepository;
import com.carrot.restaurant_vote.web.dto.CountTO;
import com.carrot.restaurant_vote.web.dto.MenuTO;
import com.carrot.restaurant_vote.web.dto.RestaurantMenuWithDish;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/1.0/restaurant")
public class MenuController extends Controller {
    private final MenuRepository menuRepository;
    private final RestaurantRepository restaurantRepository;
    private final VoteRepository voteRepository;

    public MenuController(
            MenuRepository menuRepository,
            RestaurantRepository restaurantRepository,
            VoteRepository voteRepository
    ) {
        this.menuRepository = menuRepository;
        this.restaurantRepository = restaurantRepository;
        this.voteRepository = voteRepository;
    }

    @Cacheable("menus")
    @GetMapping("menu/by-date/{date}")
    public Set<RestaurantMenuWithDish> findAllByDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        var count = voteRepository.findTotalVotesByDate(date).stream()
                .collect(Collectors.toMap(VoteCount::getMenuId, VoteCount::getCount));
        return menuRepository.findAllByDate(date).stream()
                .map(RestaurantMenuWithDish::fromEntity)
                .peek(m -> m.setVotes(count.getOrDefault(m.getId(), 0)))
                .collect(Collectors.toSet());
    }

    @GetMapping("{restaurantId}/menu/by-date/{date}")
    public RestaurantMenuWithDish findByDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @PathVariable Integer restaurantId
    ) {
        var menu = RestaurantMenuWithDish.fromEntity(
                getOrFail(menuRepository.findByDateAndRestaurantId(date, restaurantId))
        );

        menu.setVotes(voteRepository.countAllByMenuId(menu.getId()));
        return menu;
    }

    @PostMapping("{restaurantId}/menu")
    @Secured("ROLE_ADMIN")
    @CacheEvict(value = "menus", allEntries = true)
    public Menu create(@Valid @RequestBody MenuTO menuTO, @PathVariable Integer restaurantId) {
        return menuRepository.save(new Menu(menuTO.getDate(), getOrFail(restaurantRepository.findById(restaurantId))));
    }

    @GetMapping("menu/{id}/count-votes")
    public CountTO countVotes(@PathVariable Integer id) {
        return CountTO.builder().count(voteRepository.countAllByMenuId(id)).build();
    }
}
