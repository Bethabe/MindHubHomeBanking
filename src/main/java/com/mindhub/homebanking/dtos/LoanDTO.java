package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Loan;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LoanDTO {
    private Long id;
    private String name;
    private Double maxAmount;
    private List<Integer> payments = List.of();
    public LoanDTO(){}
    public LoanDTO(Loan loan){
        id = loan.getId();
        name = loan.getName();
        maxAmount = loan.getMaxAmount();;
        payments = loan.getPayments();
    }
    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public Double getMaxAmount() {
        return maxAmount;
    }
    public List<Integer> getPayments() {
        return payments;
    }
}
