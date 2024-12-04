package com.example.splitwise.DTOs;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class SettleUpGroupResponseDTO {
    private ResponseStatus status;
    private List<Transaction> transactions;

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public ResponseStatus getStatus() {
        return status;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setStatus(ResponseStatus status) {
        this.status = status;
    }
}
