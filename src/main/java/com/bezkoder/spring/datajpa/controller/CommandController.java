package com.bezkoder.spring.datajpa.controller;

import com.bezkoder.spring.datajpa.model.Command;
import com.bezkoder.spring.datajpa.repository.CommandRepository;
import com.bezkoder.spring.datajpa.service.CommandeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//@CrossOrigin(origins = "http://192.168.0.246:4200")
@CrossOrigin(origins = "https://smarteasyorders.com")
@RestController
@RequestMapping("/api/command")
public class CommandController {
    @Autowired
    CommandRepository commandRepository;

    private final CommandeService commandeService;

    @Autowired
    public CommandController(CommandeService commandeService) {
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

   /* @GetMapping("/commands/added-today")
    public ResponseEntity<List<Command>> getElementsAjoutesAujourdhui() {
        List<Command> elementsAjoutesAujourdhui = commandeService.getElementsAjoutesAujourdhui();

        if (elementsAjoutesAujourdhui.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            //System.out.println("-->"+elementsAjoutesAujourdhui);
            return ResponseEntity.ok(elementsAjoutesAujourdhui);
        }
    }*/

    /*@GetMapping("/commands/added-yesterday")
    public ResponseEntity<List<Command>> getElementsAjoutesHier() {
        List<Command> elementsAjoutesHier = commandeService.getElementsAjoutesHier();

        if (elementsAjoutesHier.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            //System.out.println("-->"+elementsAjoutesAujourdhui);
            return ResponseEntity.ok(elementsAjoutesHier);
        }
    }*/

   /* @GetMapping("/commands/added-this-week")
    public ResponseEntity<List<Command>> getElementsAjoutesCetteSemaine() {
        List<Command> elementsAjoutesCetteSemaine = commandeService.getElementsAjoutesParSemaine();

        if (elementsAjoutesCetteSemaine.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            //System.out.println("-->"+elementsAjoutesAujourdhui);
            return ResponseEntity.ok(elementsAjoutesCetteSemaine);
        }
    }*/

    /*@GetMapping("/commands/added-this-mounth")
    public List<Command> getElementsAjoutesCeMois() {
        return commandeService.getElementsAjoutesCeMois();
    }*/
    /*@GetMapping("/commands/getAll")
    public List<Command> getAllElements() {
        return commandeService.getAllElements();
    }*/

    /*@GetMapping("/commands/ordre-inverse")
    public List<Command> getAllCommandesOrderOrdreInverse() {
        return commandeService.getAllCommandesOrdreInverse();
    }*/



   /* @GetMapping("/commands/sum-price-today")
    public double getSommePrixAujourdhui() {
        List<Command> elements = commandeService.getElementsAjoutesAujourdhui();
        return commandeService.calculerSommePrix(elements);
    }
    @GetMapping("/commands/sum-price-yesterday")
    public double getSommePrixHier() {
        List<Command> elements = commandeService.getElementsAjoutesHier();
        return commandeService.calculerSommePrixHier(elements);
    }
*/
   /* @GetMapping("/commands/sum-price-thisweek")
    public double getSommePrixThisWeek() {
        List<Command> elements = commandeService.getElementsAjoutesParSemaine();
        return commandeService.calculerSommePrixSemaine(elements);
    }*/

   /* @GetMapping("/commands/sum-price-thismounth")
    public double getSommePrixThisMounth() {
        List<Command> elements = commandeService.getElementsAjoutesCeMois();
        return commandeService.calculerSommePrixMois(elements);
    }*/

    /*@GetMapping("/commands/somme-prix-total")
    public double getSommePrixTotal() {
        List<Command> elements = commandeService.getAllElements();
        return commandeService.calculerSommePrixTotal(elements);
    }*/
}
