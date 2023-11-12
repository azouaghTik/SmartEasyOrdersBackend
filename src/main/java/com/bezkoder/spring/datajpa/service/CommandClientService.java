package com.bezkoder.spring.datajpa.service;

import com.bezkoder.spring.datajpa.model.Command;
import com.bezkoder.spring.datajpa.repository.CommandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommandClientService {

    @Autowired
    private CommandRepository commandRepository;

    public void createCommand(Command command) {
        commandRepository.save(command);
    }
}
