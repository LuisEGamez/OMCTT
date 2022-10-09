package com.omctt.service;

import com.omctt.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> getAll();

    Optional<User> findByUsername(String username);

    Optional<User> findById(Integer id);

    boolean existsByUser(String username);

    boolean existsById(Integer id);
}
