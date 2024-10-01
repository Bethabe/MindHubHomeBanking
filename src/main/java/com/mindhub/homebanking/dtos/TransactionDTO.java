package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;

import java.time.LocalDate;

public class TransactionDTO {
    private Long id;
    private TransactionType type;
    private Double amount;
    private LocalDate date;
    private String description;
    public TransactionDTO (Transaction transaction){
        id = transaction.getId();
        type = transaction.getType();
        amount= transaction.getAmount();
        date = transaction.getDate();
        description = transaction.getDescription();
    }

    public Long getId() {
        return id;
    }
    public TransactionType getType() {
        return type;
    }
    public Double getAmount() {
        return amount;
    }
    public LocalDate getDate() {
        return date;
    }
    public String getDescription() {
        return description;
    }
}
