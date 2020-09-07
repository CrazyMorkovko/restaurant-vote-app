package com.carrot.restaurant_vote.web.controllers;

import com.carrot.restaurant_vote.models.Vote;
import com.carrot.restaurant_vote.repositories.VoteRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public Vote findByDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @PathVariable Integer userId
    ) {
        return getOrFail(voteRepository.findByDateAndUserId(date, userId));
    }
}
