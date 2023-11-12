package com.bezkoder.spring.datajpa.controller;

import com.bezkoder.spring.datajpa.model.Client;
import com.bezkoder.spring.datajpa.model.Command;
import com.bezkoder.spring.datajpa.model.User;
import com.bezkoder.spring.datajpa.repository.CommandRepository;
import com.bezkoder.spring.datajpa.repository.UserRepository;
import com.bezkoder.spring.datajpa.service.CommandeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

//@CrossOrigin(origins = "http://192.168.0.246:4200")
@CrossOrigin(origins = "https://smarteasyorders.com")
@RestController
@RequestMapping("/api/client")
public class CommanClientController {
    @Autowired
    CommandRepository commandRepository;

    @Autowired
    CommandeService commandeService;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/commands")
    public ResponseEntity<Command> createCommand(@RequestBody Command command) {
        try {
            User user = userRepository.findById(command.getUser().getId()).orElse(null);

            if (user == null) {
                // Gérer le cas où l'utilisateur n'existe pas
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
            Command _command = new Command(
                    command.getContents(),
                    command.getDate(),
                    command.getTime(),
                    command.getTable(),
                    command.getPrice(),
                    user
            );
            Command savedCommand = commandRepository.save(_command);
            return new ResponseEntity<>(savedCommand, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}



