package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.http.HttpRequest;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api")
public class AccountController {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    ClientRepository clientRepository;
    @RequestMapping(value = "/accounts")
        public List<AccountDTO> getAccounts(){
        return accountRepository.findAll().stream().map(element-> new AccountDTO(element)).collect(Collectors.toList());
    }
    @RequestMapping(value = "/accounts/{id}")
        public AccountDTO getaccount(@PathVariable Long id){
        return new AccountDTO(accountRepository.findById(id).orElse(null));
    }
    @PostMapping(value = "/clients/current/accounts")
        public ResponseEntity<Object> createAccount(Authentication authentication){
        if (authentication !=null){
            Client client = clientRepository.findByEmail(authentication.getName());
            Set<Account> setAccounts = client.getAccounts();
            int cantCuentas = setAccounts.size();
            String number;
            if (cantCuentas >= 3){
                return new ResponseEntity<>("Ya posee el número máximo de cuentas permitidas", HttpStatus.FORBIDDEN);
            }else{
                Account account = new Account();
                do{
                    number =  String.format("VIN-%d", (int) (Math.random() * (99999999 - 10000000) + 10000000));
                }while(accountRepository.existsByNumber(number));
                account.setNumber(number);
                client.addAccount(account);
                accountRepository.save(account);
                return new ResponseEntity<>(HttpStatus.CREATED);
        }
        }else {
            return  new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }


}
