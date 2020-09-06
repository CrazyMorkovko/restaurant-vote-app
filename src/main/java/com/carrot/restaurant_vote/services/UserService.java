package com.carrot.restaurant_vote.services;

import com.carrot.restaurant_vote.repositories.UserRepository;
import com.carrot.restaurant_vote.security.AuthorizedUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        var user = userRepository.findByName(name);

        if (user == null) {
            throw new UsernameNotFoundException("User " + name + " not found.");
        }

        return new AuthorizedUser(user);
    }

    public boolean isEmailExist(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean isNameExist(String name) {
        return userRepository.existsByName(name);
    }
}
