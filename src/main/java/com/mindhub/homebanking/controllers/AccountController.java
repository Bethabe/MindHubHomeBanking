package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api")
public class AccountController {
    @Autowired
    AccountRepository accountRepository;

    @RequestMapping(value = "/accounts")
        public List<AccountDTO> getAccounts(){
        return accountRepository.findAll().stream().map(element-> new AccountDTO(element)).collect(Collectors.toList());
    }
    @RequestMapping(value = "/accounts/{id}")
        public AccountDTO getaccount(@PathVariable Long id){
        return new AccountDTO(accountRepository.findById(id).orElse(null));
        }
}
