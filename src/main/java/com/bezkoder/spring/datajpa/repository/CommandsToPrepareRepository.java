package com.bezkoder.spring.datajpa.repository;

import com.bezkoder.spring.datajpa.model.CommandsToPrepare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommandsToPrepareRepository extends JpaRepository<CommandsToPrepare, Long> {

    @Query("SELECT s FROM CommandsToPrepare s WHERE s.user.id = :userId")
    List<CommandsToPrepare> getCommandsByUser(Long userId);

    @Query("SELECT s FROM CommandsToPrepare s WHERE s.user.id = :userId ORDER BY s.id DESC")
    List<CommandsToPrepare> getInversedCommandsByUser(Long userId);


}
