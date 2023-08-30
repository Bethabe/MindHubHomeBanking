package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping(value = "/api")
public class CardController {
    @Autowired
    CardRepository cardRepository;
    @Autowired
    ClientRepository clientRepository;

    @PostMapping(value = "/clients/current/cards")
    ResponseEntity<Object> createCard(Authentication authentication,  @RequestParam CardType type, @RequestParam CardColor color)
    {
        Client client =  clientRepository.findByEmail(authentication.getName());
        Card card = new Card(type,color);
        card.setCardHolder(client.getFirstName()+" "+client.getLastName());
        card.setFromDate(LocalDate.now());
        card.setThruDate(LocalDate.now().plusYears(5));
        card.setCvv((int) (Math.random()*(400-200)+200));
        String aleatorios1 = String.valueOf((int)(Math.random()*(9000-1000)+1000));
        String aleatorios2 = String.valueOf((int)(Math.random()*(9000-1000)+1000));
        String aleatorios3 = String.valueOf((int)(Math.random()*(9000-1000)+1000));
        String aleatorios4 = String.valueOf((int)(Math.random()*(9000-1000)+1000));
        card.setNumber(aleatorios1+"-"+aleatorios2+"-"+aleatorios3+"-"+aleatorios4);
        client.addCard(card);
        cardRepository.save(card);
        return new ResponseEntity<>("Correcto", HttpStatus.CREATED);
    }
}
