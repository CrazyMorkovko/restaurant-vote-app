package com.carrot.restaurant_vote.web.controllers;

import com.carrot.restaurant_vote.models.Vote;
import com.carrot.restaurant_vote.repositories.VoteRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/1.0/user/{userId}/vote")
public class VoteController extends Controller {
    private final VoteRepository voteRepository;

    public VoteController(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    @GetMapping
    public Iterable<Vote> findAll(@PathVariable Integer userId) {
        return voteRepository.findAllByUserId(userId);
    }

    @GetMapping("by-date/{date}")
    public Vote findByDate(@PathVariable LocalDate date, @PathVariable Integer userId) {
        return getOrFail(voteRepository.findByDateAndUserId(date, userId));
    }
}
