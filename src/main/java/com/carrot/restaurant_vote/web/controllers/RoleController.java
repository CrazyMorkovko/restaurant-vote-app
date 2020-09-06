package com.carrot.restaurant_vote.web.controllers;

import com.carrot.restaurant_vote.models.Role;
import com.carrot.restaurant_vote.repositories.RoleRepository;
import com.carrot.restaurant_vote.repositories.UserRepository;
import com.carrot.restaurant_vote.web.dto.RoleTo;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Secured("ROLE_ADMIN")
@RequestMapping("/api/1.0/user/{userId}/role")
public class RoleController extends Controller {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public RoleController(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @GetMapping
    public Iterable<Role> findAllByUserId(@PathVariable Integer userId) {
        return getOrFail(userRepository.findById(userId)).getRoles();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addRole(@Valid @RequestBody RoleTo roleTo, @PathVariable Integer userId) {
        var user = getOrFail(userRepository.findById(userId));

        user.getRoles().add(getOrFail(roleRepository.findByName(roleTo.getRole())));
        userRepository.save(user);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRole(@Valid @RequestBody RoleTo roleTo, @PathVariable Integer userId) {
        var user = getOrFail(userRepository.findById(userId));

        user.getRoles().remove(getOrFail(roleRepository.findByName(roleTo.getRole())));
        userRepository.save(user);
    }
}
