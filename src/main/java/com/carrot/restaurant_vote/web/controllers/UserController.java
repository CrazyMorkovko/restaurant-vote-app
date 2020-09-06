package com.carrot.restaurant_vote.web.controllers;

import com.carrot.restaurant_vote.models.User;
import com.carrot.restaurant_vote.models.Vote;
import com.carrot.restaurant_vote.repositories.MenuRepository;
import com.carrot.restaurant_vote.repositories.UserRepository;
import com.carrot.restaurant_vote.repositories.VoteRepository;
import com.carrot.restaurant_vote.web.dto.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@RestController
@RequestMapping("/api/1.0/user")
public class UserController extends Controller {
    private final UserRepository userRepository;
    private final VoteRepository voteRepository;
    private final MenuRepository menuRepository;
    private final PasswordEncoder passwordEncoder;

    public UserController(
            UserRepository userRepository,
            VoteRepository voteRepository,
            MenuRepository menuRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.voteRepository = voteRepository;
        this.menuRepository = menuRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/profile")
    public UserProfile profile(Authentication authentication) {
        var user = getUser(authentication);
        var vote = voteRepository.findByDateAndUserId(LocalDate.now(), user.getId());

        return UserProfile.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .registered(user.getRegistered())
                .todayVote(vote.orElse(null))
                .build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void registration(@Valid @RequestBody CreateUser createUser) {
        userRepository.save(new User(
                createUser.getName(),
                createUser.getEmail(),
                passwordEncoder.encode(createUser.getPassword()),
                true,
                LocalDateTime.now()
        ));
    }

    @PutMapping("email")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateEmail(@Valid @RequestBody UpdateEmail updateEmail, Authentication authentication) {
        var user = getUser(authentication);

        user.setEmail(updateEmail.getEmail());
        userRepository.save(user);
    }

    @PutMapping("password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changePassword(@Valid @RequestBody ChangePassword changePassword, Authentication authentication) {
        var user = getUser(authentication);

        user.setPassword(passwordEncoder.encode(changePassword.getPassword()));
        userRepository.save(user);
    }

    @PostMapping("vote")
    public Vote createVote(@Valid @RequestBody VoteTO voteTO, Authentication authentication) {
        var user = getUser(authentication);

        if (voteRepository.findByDateAndUserId(LocalDate.now(), user.getId()).isEmpty()) {
            return voteRepository.save(new Vote(
                    LocalDate.now(),
                    user,
                    getOrFail(menuRepository.findById(voteTO.getMenuId()))
            ));
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("vote")
    public Vote updateVote(@Valid @RequestBody VoteTO voteTO, Authentication authentication) {
        var user = getUser(authentication);
        var updateVote = getOrFail(voteRepository.findByDateAndUserId(LocalDate.now(), user.getId()));

        if (updateVote.getUser().getId().equals(user.getId()) && updateVote.getDate().equals(LocalDate.now())
                && LocalTime.now().isBefore(LocalTime.of(11, 0))) {
            updateVote.setMenu(getOrFail(menuRepository.findById(voteTO.getMenuId())));
            return voteRepository.save(updateVote);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("vote")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteVote(Authentication authentication) {
        var user = getUser(authentication);
        var vote = getOrFail(voteRepository.findByDateAndUserId(LocalDate.now(), user.getId()));

        if (vote.getUser().getId().equals(user.getId()) && LocalTime.now().isBefore(LocalTime.of(11, 0))) {
            voteRepository.delete(vote);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }
}
