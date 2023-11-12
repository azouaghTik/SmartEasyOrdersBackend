package com.bezkoder.spring.datajpa.repository;

import com.bezkoder.spring.datajpa.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findByName(String name);
}
