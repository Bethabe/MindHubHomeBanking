package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.TransactionDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
@RestController
@RequestMapping(value ="/api")
public class TransactionController {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Transactional
    @PostMapping(value="/transactions")
    public ResponseEntity<Object> registerTransaction(Authentication authentication, @RequestParam String description, @RequestParam Double amount, @RequestParam String fromAccountNumber, @RequestParam String toAccountNumber)
    {
        if(authentication !=null){
            if(fromAccountNumber.isBlank() || toAccountNumber.isBlank() || description.isBlank() ||amount.isNaN()){
                return new ResponseEntity<>("Existe al menos un campo vacío.", HttpStatus.FORBIDDEN);
            }
            if (accountRepository.existsByNumber(fromAccountNumber) && accountRepository.existsByNumber(toAccountNumber)){
                Transaction transactionOrigin = new Transaction(TransactionType.DEBIT,amount,description);
                Transaction transactionDestiny= new Transaction(TransactionType.CREDIT, amount, description);
                Account originAccount = accountRepository.findByNumber(fromAccountNumber);
                Account destinyAccount = accountRepository.findByNumber(toAccountNumber);
                if (originAccount.getId().equals(destinyAccount.getId())){
                    return new ResponseEntity<>("Las cuentas de destino y origen son las mismas", HttpStatus.FORBIDDEN);
                }
                if (originAccount.getBalance()>=amount){
                    originAccount.addTransactions(transactionOrigin);
                    destinyAccount.addTransactions(transactionDestiny);

                    destinyAccount.setBalance(destinyAccount.getBalance() + amount);
                    originAccount.setBalance(originAccount.getBalance() - amount);
                    transactionRepository.save(transactionOrigin);
                    transactionRepository.save(transactionDestiny);
                }else{
                 return  new ResponseEntity<>("Not enough amounts to transfer", HttpStatus.FORBIDDEN);
                }
            }else{
                return new ResponseEntity<>("", HttpStatus.FORBIDDEN);
            }
            return new ResponseEntity<>("Transacción exitosa", HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>("Cliente no encontrado",HttpStatus.FORBIDDEN);
        }
    }
}
