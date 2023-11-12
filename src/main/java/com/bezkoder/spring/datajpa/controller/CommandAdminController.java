package com.bezkoder.spring.datajpa.controller;

import com.bezkoder.spring.datajpa.model.Command;
import com.bezkoder.spring.datajpa.repository.CommandRepository;
import com.bezkoder.spring.datajpa.security.services.UserDetailsImpl;
import com.bezkoder.spring.datajpa.service.CommandeService;
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
@RequestMapping("/api/admin")
public class CommandAdminController {
    @Autowired
    CommandRepository commandRepository;

    private final CommandeService commandeService;

    @Autowired
    public CommandAdminController(CommandeService commandeService) {
        this.commandeService = commandeService;
    }

    @GetMapping("/commands")
    public ResponseEntity<List<Command>> getAllCommands(@RequestParam(required = false) String title) {
        try {
            List<Command> commands = new ArrayList<Command>();
            commandRepository.findAll().forEach(commands::add);
            if (commands.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(commands, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/commands/{id}")
    public ResponseEntity<Command> getCommandById(@PathVariable("id") long id) {
        Optional<Command> commandData = commandRepository.findById(id);

        if (commandData.isPresent()) {
            return new ResponseEntity<>(commandData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

   /* @PostMapping("/commands")
    public ResponseEntity<Command> createCommand(@RequestBody Command command) {
        try {
            Command _command = commandRepository
                    .save(new Command(
                            command.getContents(),
                            command.getDate(),
                            command.getTime(),
                            command.getTable(),
                            command.getPrice()
                    ));
            return new ResponseEntity<>(_command, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/

    @PutMapping("/commands/{id}")
    public ResponseEntity<Command> updateCommand(@PathVariable("id") long id, @RequestBody Command command) {
        Optional<Command> commandData = commandRepository.findById(id);

        if (commandData.isPresent()) {
            Command _command = commandData.get();
            _command.setContents(command.getContents());
            _command.setDate(command.getDate());
            _command.setTime(command.getTime());
            _command.setTable(command.getTable());
            _command.setPrice(command.getPrice());
            return new ResponseEntity<>(commandRepository.save(_command), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/commands/{id}")
    public ResponseEntity<HttpStatus> deleteCommand(@PathVariable("id") long id) {
        try {
            commandRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/commands")
    public ResponseEntity<HttpStatus> deleteAllCommands() {
        try {
            commandRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/commands/added-today")
    public ResponseEntity<List<Command>> getElementsAjoutesAujourdhui() {
        // Obtenir l'objet Authentication de l'utilisateur actuellement authentifié
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Vérifier si l'utilisateur est authentifié
        if (authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetailsImpl) {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            Long user_id = userDetails.getId();

            List<Command> elementsAjoutesAujourdhui = commandeService.getElementsAjoutesAujourdhui(user_id);

            if (elementsAjoutesAujourdhui.isEmpty()) {
                return ResponseEntity.noContent().build();
            } else {
                //System.out.println("-->"+elementsAjoutesAujourdhui);
                return ResponseEntity.ok(elementsAjoutesAujourdhui);
            }
        } else return null;
    }

    @GetMapping("/commands/added-yesterday")
    public ResponseEntity<List<Command>> getElementsAjoutesHier() {
        // Obtenir l'objet Authentication de l'utilisateur actuellement authentifié
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Vérifier si l'utilisateur est authentifié
        if (authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetailsImpl) {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            Long user_id = userDetails.getId();

            List<Command> elementsAjoutesHier = commandeService.getElementsAjoutesHier(user_id);

            if (elementsAjoutesHier.isEmpty()) {
                return ResponseEntity.noContent().build();
            } else {
                //System.out.println("-->"+elementsAjoutesAujourdhui);
                return ResponseEntity.ok(elementsAjoutesHier);
            }
        } else return null;
    }

    @GetMapping("/commands/added-this-week")
    public ResponseEntity<List<Command>> getElementsAjoutesCetteSemaine() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetailsImpl) {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            Long user_id = userDetails.getId();
        List<Command> elementsAjoutesCetteSemaine = commandeService.getElementsAjoutesParSemaine(user_id);

        if (elementsAjoutesCetteSemaine.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            //System.out.println("-->"+elementsAjoutesAujourdhui);
            return ResponseEntity.ok(elementsAjoutesCetteSemaine);
        }
    }else return null;
    }

    @GetMapping("/commands/added-this-mounth")
    public List<Command> getElementsAjoutesCeMois() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetailsImpl) {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            Long user_id = userDetails.getId();
        return commandeService.getElementsAjoutesCeMois(user_id);
    }
        else return null;
    }
    /*@GetMapping("/commands/getAll")
    public List<Command> getAllElements() {
        return commandeService.getAllElements();
    }*/

    @GetMapping("/commands/ordre-inverse")
    public List<Command> getAllCommandesOrderOrdreInverse() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetailsImpl) {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            Long user_id = userDetails.getId();
        return commandeService.getAllCommandesOrdreInverse(user_id);
    }
        else return null;
    }

    @GetMapping("/commands/sum-price-today")
    public double getSommePrixAujourdhui() {
        // Obtenir l'objet Authentication de l'utilisateur actuellement authentifié
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Vérifier si l'utilisateur est authentifié
        if (authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetailsImpl) {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            Long user_id = userDetails.getId();
            List<Command> elements = commandeService.getElementsAjoutesAujourdhui(user_id);
            return commandeService.calculerSommePrix(elements);

        }
        else return 0;
    }

    @GetMapping("/commands/sum-price-yesterday")
    public double getSommePrixHier() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetailsImpl) {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            Long user_id = userDetails.getId();
            List<Command> elements = commandeService.getElementsAjoutesHier(user_id);
            return commandeService.calculerSommePrixHier(elements);
        }
        else return 0;
    }

    @GetMapping("/commands/sum-price-thisweek")
    public double getSommePrixThisWeek() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetailsImpl) {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            Long user_id = userDetails.getId();
        List<Command> elements = commandeService.getElementsAjoutesParSemaine(user_id);
        return commandeService.calculerSommePrixSemaine(elements);
    }else return 0;
    }
    @GetMapping("/commands/sum-price-thismounth")
        public double getSommePrixThisMounth() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetailsImpl) {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            Long user_id = userDetails.getId();
        List<Command> elements = commandeService.getElementsAjoutesCeMois(user_id);
        return commandeService.calculerSommePrixMois(elements);
    }else return 0;
    }

    @GetMapping("/commands/somme-prix-total")
    public double getSommePrixTotal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetailsImpl) {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            Long user_id = userDetails.getId();
        List<Command> elements = commandeService.getAllElements(user_id);
        return commandeService.calculerSommePrixTotal(elements);
    }else return 0;
    }
}
