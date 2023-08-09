package com.mindhub.homebanking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name ="native", strategy = "native")
    private Long id;
    private TransactionType type;
    private Double amount;
    private LocalDate date;
    private String description;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_account")
    private Account account;
    public Transaction(){
    }
    public Transaction(TransactionType type, Double amount, String description){
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.date = LocalDate.now();
    }
    public Long getId() {
        return id;
    }
    public Account getAccount() {
        return account;
    }
    public void setAccount(Account account) {
        this.account = account;
    }
    public TransactionType getType() {
        return type;
    }
    public void setType(TransactionType type) {
        this.type = type;
    }
    public Double getAmount() {
        return amount;
    }
    public void setAmount(Double amount) {
        this.amount = amount;
    }
    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
