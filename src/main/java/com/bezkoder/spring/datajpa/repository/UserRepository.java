package com.bezkoder.spring.datajpa.repository;


import com.bezkoder.spring.datajpa.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);

	User getUserById(Long user);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);
}
