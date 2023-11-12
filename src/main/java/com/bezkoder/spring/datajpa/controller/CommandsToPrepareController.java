package com.bezkoder.spring.datajpa.controller;

import com.bezkoder.spring.datajpa.model.Command;
import com.bezkoder.spring.datajpa.model.CommandsToPrepare;
import com.bezkoder.spring.datajpa.model.Food;
import com.bezkoder.spring.datajpa.model.User;
import com.bezkoder.spring.datajpa.repository.CommandsToPrepareRepository;
import com.bezkoder.spring.datajpa.repository.UserRepository;
import com.bezkoder.spring.datajpa.security.services.UserDetailsImpl;
import com.bezkoder.spring.datajpa.service.CommandeService;
import com.bezkoder.spring.datajpa.service.CommandsToPrepareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//@CrossOrigin(origins = "http://192.168.0.246:4200")
@CrossOrigin(origins = "https://smarteasyorders.com")
@RestController
@RequestMapping("/api/command")
public class CommandsToPrepareController {
    @Autowired
    CommandsToPrepareRepository commandsToPrepareRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CommandsToPrepareService commandsToPrepareService;

    @Autowired
    CommandeService commandeService;

    /*@GetMapping("/commandsToPrepare/getAll")
    public ResponseEntity<List<CommandsToPrepare>> getAllCommands() {
        try {
            List<CommandsToPrepare> commands = new ArrayList<CommandsToPrepare>();
            commandsToPrepareRepository.findAll().forEach(commands::add);
            return new ResponseEntity<>(commands, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/

    @GetMapping("/commandsToPrepare/getByUser/{user_id}")
    public ResponseEntity<List<CommandsToPrepare>> getCommandsByUser(@PathVariable Long user_id) {
        try {
            List<CommandsToPrepare> commands = new ArrayList<CommandsToPrepare>();
            commandsToPrepareRepository.getCommandsByUser(user_id).forEach(commands::add);
            return new ResponseEntity<>(commands, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/commandsToPrepare/getInversedByUser/{user_id}")
    public ResponseEntity<List<CommandsToPrepare>> getInversedCommandsByUser(@PathVariable Long user_id) {
        try {
            List<CommandsToPrepare> commands = new ArrayList<CommandsToPrepare>();
            commandsToPrepareRepository.getInversedCommandsByUser(user_id).forEach(commands::add);
            return new ResponseEntity<>(commands, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/commandsToPrepare/ordres-inverse/{user_id}")
    public List<Command> getAllCommandesOrderOrdreInverse(@PathVariable Long user_id) {
            return commandeService.getAllCommandesOrdreInverse(user_id);
    }


    @PostMapping("/commandsToPrepare")
    public ResponseEntity<CommandsToPrepare> createCommand(@RequestBody CommandsToPrepare commandsToPrepare) {
        try {
            User user = userRepository.findById(commandsToPrepare.getUser().getId()).orElse(null);

            if (user == null) {
                // Gérer le cas où l'utilisateur n'existe pas
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
            CommandsToPrepare command = new CommandsToPrepare(
                    commandsToPrepare.getContents(),
                    commandsToPrepare.getDate(),
                    commandsToPrepare.getTime(),
                    commandsToPrepare.getTable(),
                    commandsToPrepare.getPrice(),
                    user
            );
            CommandsToPrepare savedCommand = commandsToPrepareRepository.save(command);
            return new ResponseEntity<>(savedCommand, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/commandsToPrepare/deleteCommand/{id}")
    public ResponseEntity<HttpStatus> deleteCommand(@PathVariable("id") long id) {
        try {
            CommandsToPrepare commandsToPrepare = commandsToPrepareRepository.findById(id).orElse(null);

            if (commandsToPrepare != null) {
                commandsToPrepare.setUser(null); // Supprimez l'association avec l'utilisateur
                commandsToPrepareRepository.save(commandsToPrepare); // Mettez à jour l'enregistrement Food
                commandsToPrepareRepository.deleteById(id);
            }


            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
 }
