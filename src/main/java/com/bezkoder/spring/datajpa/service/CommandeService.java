package com.bezkoder.spring.datajpa.service;

import com.bezkoder.spring.datajpa.model.Command;
import com.bezkoder.spring.datajpa.repository.CommandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;


@Service
public class CommandeService {

    private final CommandRepository commandRepository;

    @Autowired
    public CommandeService(CommandRepository commandRepository) {
        this.commandRepository = commandRepository;
    }

    public List<Command> getElementsAjoutesAujourdhui(Long userId) {
        LocalDateTime now = LocalDateTime.now();

        // Créer un objet DateTimeFormatter avec le format souhaité
       // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        // Formater la date et l'heure dans le format spécifié
        String dateEtHeureFormatees = now.format(formatter);
        return commandRepository.findElementsAjoutesAujourdhui(dateEtHeureFormatees, userId);
    }

    public List<Command> getElementsAjoutesHier(Long userId) {
        LocalDate hier = LocalDate.now().minusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String yesterdayDateFormatees = hier.format(formatter);
        return commandRepository.findElementsAjoutesAujourdhui(yesterdayDateFormatees,userId);
    }


    public List<Command> getElementsAjoutesParSemaine(Long userId) {
        LocalDate dateDebutSemaine = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate dateFinSemaine = LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dateDebutSemaineDateFormatees = dateDebutSemaine.format(formatter);
        String dateFinSemaineDateFormatees = dateFinSemaine.format(formatter);

        return commandRepository.findElementsAjoutesCetteSemaine(dateDebutSemaineDateFormatees, dateFinSemaineDateFormatees, userId);
    }

    public List<Command> getElementsAjoutesCeMois(Long userId) {
        LocalDate now = LocalDate.now();
        LocalDate startOfMonth = now.withDayOfMonth(1);
        LocalDate endOfMonth = now.withDayOfMonth(now.lengthOfMonth());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dateDebutMoisDateFormatees = startOfMonth.format(formatter);
        String dateFinMoisDateFormatees = endOfMonth.format(formatter);

        return commandRepository.findElementsAjoutesCeMois(dateDebutMoisDateFormatees, dateFinMoisDateFormatees,userId);
    }

    public List<Command> getAllElements(Long userId) {
        return commandRepository.getAllSum(userId);
    }

    public List<Command> getAllCommandesOrdreInverse(Long userId) {
        return commandRepository.findAllCommandesOrdreInverse(userId);
    }

    /* --------------------------------------------------  */

    public double calculerSommePrix(List<Command> elements) {
        double somme = 0.0;
        for (Command element : elements) {
            somme += element.getPrice();
        }
        return somme;
    }

    public double calculerSommePrixHier(List<Command> elements) {
        double somme = 0.0;
        for (Command element : elements) {
            somme += element.getPrice();
        }
        return somme;
    }

    public double calculerSommePrixSemaine(List<Command> elements) {
        double somme = 0.0;
        for (Command element : elements) {
            somme += element.getPrice();
        }
        return somme;
    }

    public double calculerSommePrixMois(List<Command> elements) {
        double somme = 0.0;
        for (Command element : elements) {
            somme += element.getPrice();
        }
        return somme;
    }

    public double calculerSommePrixTotal(List<Command> elements) {
        double somme = 0.0;
        for (Command element : elements) {
            somme += element.getPrice();
        }
        return somme;
    }

}
