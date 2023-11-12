package com.bezkoder.spring.datajpa.controller;

import com.bezkoder.spring.datajpa.model.Food;
import com.bezkoder.spring.datajpa.model.Settings;
import com.bezkoder.spring.datajpa.repository.FoodRepository;
import com.bezkoder.spring.datajpa.repository.SettingsRepository;
import com.bezkoder.spring.datajpa.security.services.UserDetailsImpl;
import com.bezkoder.spring.datajpa.service.FoodService;
import com.bezkoder.spring.datajpa.service.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

//@CrossOrigin(origins = "http://192.168.0.246:4200")
@CrossOrigin(origins = "https://smarteasyorders.com")
@RestController
@RequestMapping("/api/admin")
public class SettingsController {
    @Autowired
    SettingsRepository settingsRepository;

    @Autowired
    SettingsService settingsService;



    @PostMapping("/settings")
    public ResponseEntity<?> addSettings(@RequestBody Settings settings) {

        // Récupérez l'utilisateur authentifié
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetailsImpl) {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            // Obtenir l'ID de l'utilisateur authentifié à partir de UserDetailsImpl
            Long userid = userDetails.getId();
            settingsService.addSettings(settings, userid);

            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            // Gérez le cas où l'utilisateur n'est pas authentifié ou où les informations de l'utilisateur ne sont pas disponibles
            // Vous pouvez renvoyer une erreur ou rediriger vers une page de connexion
            return null;
        }
    }

}

