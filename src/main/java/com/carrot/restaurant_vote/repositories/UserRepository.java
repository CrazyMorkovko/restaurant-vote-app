package com.carrot.restaurant_vote.repositories;

import com.carrot.restaurant_vote.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    User findByName(String name);

    boolean existsByEmail(String email);

    boolean existsByName(String name);
}
