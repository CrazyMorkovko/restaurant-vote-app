package com.carrot.restaurant_vote.web.controllers;

import com.carrot.restaurant_vote.models.User;
import com.carrot.restaurant_vote.security.AuthorizedUser;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

public abstract class Controller {
    public User getUser(Authentication authentication) {
        return ((AuthorizedUser) authentication.getPrincipal()).getUser();
    }

    public <T> T getOrFail(Optional<T> optional) {
        return optional.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
