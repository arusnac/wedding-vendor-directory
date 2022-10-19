package com.arusnac.weddingplanner.repository;

import com.arusnac.weddingplanner.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepo extends CrudRepository<User, Integer> {
    public Optional<User> findByEmail(String email);
}
