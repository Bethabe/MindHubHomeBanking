package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.models.Loan;

public class ClientLoanDTO {
    private Long id;
    private Integer payments;
    private  Double amount;
    private String name;
    private Long idLoan;

    public ClientLoanDTO(ClientLoan clientLoan){
        id = clientLoan.getId();
        idLoan = clientLoan.getLoan().getId();
        name = clientLoan.getLoan().getName();
        payments = clientLoan.getPayment();
        amount = clientLoan.getAmount();
    }

    public Long getId() {
        return id;
    }

    public Integer getPayments() {
        return payments;
    }

    public Double getAmount() {
        return amount;
    }

    public String getName() {
        return name;
    }

    public Long getIdLoan() {
        return idLoan;
    }
}
