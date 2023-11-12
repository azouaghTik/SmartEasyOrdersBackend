package com.bezkoder.spring.datajpa.service;

import com.bezkoder.spring.datajpa.model.Food;
import com.bezkoder.spring.datajpa.model.Settings;
import com.bezkoder.spring.datajpa.model.User;
import com.bezkoder.spring.datajpa.repository.FoodRepository;
import com.bezkoder.spring.datajpa.repository.SettingsRepository;
import com.bezkoder.spring.datajpa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SettingsService {

    @Autowired
    SettingsRepository settingsRepository;

    @Autowired
    UserRepository userRepository;

    public void addSettings(Settings settings, Long userid) {
        // Récupérez l'utilisateur à partir de la base de données en fonction du nom d'utilisateur
        User user = userRepository.getUserById(userid);

        if (user != null) {
            // Associez l'utilisateur à l'aliment
            settings.setUser(user);
            settingsRepository.save(settings);
        } else {
            throw new IllegalArgumentException("Utilisateur non trouvé : " + userid);
        }
    }


}
