package com.carrot.restaurant_vote.repositories;

import com.carrot.restaurant_vote.models.Role;
import com.carrot.restaurant_vote.security.RoleType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {
    Optional<Role> findByName(RoleType name);
}
