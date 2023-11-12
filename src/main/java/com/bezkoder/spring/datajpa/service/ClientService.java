package com.bezkoder.spring.datajpa.service;

import com.bezkoder.spring.datajpa.model.Client;
import com.bezkoder.spring.datajpa.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ClientService{

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

}
