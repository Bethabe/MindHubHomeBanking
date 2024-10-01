package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api")
public class LoanController {
    @Autowired
    LoanRepository loanRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    ClientLoanRepository clientLoanRepository;
    @Autowired
    TransactionRepository transactionRepository;
    @RequestMapping(value="/loans")
    public List<LoanDTO> listarPrestamos(){
        return  loanRepository.findAll().stream().map(loan -> new LoanDTO(loan)).collect(Collectors.toList());
    }
   @Transactional
   @PostMapping(value="/loans")
   public ResponseEntity<String> solicitarPrestamos(@RequestBody LoanApplicationDTO loanApplicationDTO, Authentication authentication){

       String account = loanApplicationDTO.getToAccountNumber();
       Integer payments = loanApplicationDTO.getPayments();
       Double amount = loanApplicationDTO.getAmount();
       Long loanTypeId = loanApplicationDTO.getLoanId();

       if(authentication!=null){
            Client client = clientRepository.findByEmail(authentication.getName());
            if(account.isBlank() || payments == null|| amount == null || loanTypeId == null){
                return new ResponseEntity<>("Algún campo se encuentra vacio",HttpStatus.FORBIDDEN);
            }
            if(loanRepository.existsById(loanTypeId)){
                Loan loan =  loanRepository.findById(loanTypeId).orElse(null);
                if(loanApplicationDTO.getAmount() <= loan.getMaxAmount()){
                     if(loan.getPayments().stream().filter(x -> x.equals(loanApplicationDTO.getPayments())).count()==1){
                         if (accountRepository.existsByNumber(account)){
                             Account cuenta = accountRepository.findByNumber(account);
                             if (cuenta.getClient().equals(client)){
                                 ClientLoan clientLoan = new ClientLoan(payments,amount*1.2,client,loan);
                                 clientLoanRepository.save(clientLoan);
                                 Transaction transaction = new Transaction(TransactionType.CREDIT,amount,loan.getName()+"loan approved");
                                 transactionRepository.save(transaction);
                                 cuenta.setBalance(cuenta.getBalance() + amount);
                             }else {
                                 return new ResponseEntity<>("Esta cuenta no pertenece al cliente autentificado", HttpStatus.FORBIDDEN);
                             }
                         }else {
                             return new ResponseEntity<>("No existe la cuenta de destino", HttpStatus.FORBIDDEN);
                         }
                    }else{
                         return new ResponseEntity<>("No existe esa cantidad de cuotas", HttpStatus.FORBIDDEN);
                     }
                }else {
                    return new ResponseEntity<>("El monto solicitado es mayor al máximo permitido", HttpStatus.FORBIDDEN);
                }

            }else{
                return new ResponseEntity<>("No exite ese tipo de prestamo", HttpStatus.FORBIDDEN);
            }

        }else{
            return new ResponseEntity<>("Usuario no autenticado", HttpStatus.FORBIDDEN);
        }
       return new ResponseEntity<>("Préstamo Aprobado", HttpStatus.ACCEPTED);
   }
}

