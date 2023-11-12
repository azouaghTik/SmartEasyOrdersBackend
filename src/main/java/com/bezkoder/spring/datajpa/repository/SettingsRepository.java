package com.bezkoder.spring.datajpa.repository;

import com.bezkoder.spring.datajpa.model.Settings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SettingsRepository extends JpaRepository<Settings, Long> {

}
