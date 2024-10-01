package com.mindhub.homebanking.dtos;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public class LoanApplicationDTO {
    private Integer payments;
    private  Double amount;
    private Long loanId;
    private String toAccountNumber;
    public LoanApplicationDTO(Integer payments, Double amount, Long loanId, String toAccountNumber){
            this.payments = payments;
            this.amount = amount;
            this.loanId = loanId;
            this.toAccountNumber = toAccountNumber;
    }

    public Integer getPayments() {
        return payments;
    }

    public Double getAmount() {
        return amount;
    }
    public Long getLoanId() {
        return loanId;
    }
    public String getToAccountNumber() {
        return toAccountNumber;
    }
}
