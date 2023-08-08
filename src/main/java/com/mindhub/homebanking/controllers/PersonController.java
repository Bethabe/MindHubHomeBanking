package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api")
public class PersonController {
    @Autowired
    ClientRepository clientRepository;
    @RequestMapping(value = "/clients")
    public List<ClientDTO> getClients() {

        return clientRepository.findAll().stream().map(element -> new ClientDTO(element)).collect(Collectors.toList());
    }

    @RequestMapping(value = "/clients/{id}")
    public ClientDTO getPerson(@PathVariable Long id){
        return new ClientDTO(clientRepository.findById(id).orElse(null));
    }
}


