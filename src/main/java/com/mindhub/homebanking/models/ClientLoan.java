package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class ClientLoan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name= "native",strategy = "native")
    private Long id;
    private Integer payment;
    private  Double amount;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idClient")
    private Client client;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idLoan")
    private Loan loan;
    public ClientLoan(){
    }
    public ClientLoan(Integer payment, Double amount, Client client, Loan loan){
        this.payment = payment;
        this.amount = amount;
        this.client = client;
        this.loan = loan;
    }
    public Long getId() {
        return id;
    }
    public Integer getPayment() {
        return payment;
    }
    public void setPayment(Integer payment) {
        this.payment = payment;
    }
    public Double getAmount() {
        return amount;
    }
    public void setAmount(Double amount) {
        this.amount = amount;
    }
    public Client getClient() {
        return client;
    }
    public void setClient(Client client) {
        this.client = client;
    }
    public Loan getLoan() {
        return loan;
    }
    public void setLoan(Loan loan) {
        this.loan = loan;
    }
}
