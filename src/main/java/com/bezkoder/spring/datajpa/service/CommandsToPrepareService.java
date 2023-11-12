package com.bezkoder.spring.datajpa.service;

import com.bezkoder.spring.datajpa.model.Command;
import com.bezkoder.spring.datajpa.model.CommandsToPrepare;
import com.bezkoder.spring.datajpa.repository.CommandRepository;
import com.bezkoder.spring.datajpa.repository.CommandsToPrepareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CommandsToPrepareService {

    /*private final CommandsToPrepareRepository commandsToPrepareRepository;
    private final CommandRepository commandRepository;

    @Autowired
    public CommandsToPrepareService(CommandsToPrepareRepository commandsToPrepareRepository, CommandRepository commandRepository, CommandRepository commandRepository1) {
        this.commandsToPrepareRepository = commandsToPrepareRepository;
        //this.commandRepository = commandRepository;
        this.commandRepository = commandRepository1;
    }

    public List<CommandsToPrepare> getAllElements() {
        return commandsToPrepareRepository.findAll();
    }*/

}
