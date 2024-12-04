package com.example.splitwise.DTOs;

import java.util.List;

public class SettleUpUserResponseDTO {
    private ResponseStatus status;
    private List<Transaction> transactions;

    public void setStatus(ResponseStatus status) {
        this.status = status;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public ResponseStatus getStatus() {
        return status;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}
