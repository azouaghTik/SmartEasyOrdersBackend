package com.bezkoder.spring.datajpa.service;

import com.bezkoder.spring.datajpa.model.User;

import java.util.Optional;

public interface  UserService {
    Optional<User> getUserById(Long userId);

}
