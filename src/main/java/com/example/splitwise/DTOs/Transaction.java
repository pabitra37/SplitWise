package com.example.splitwise.DTOs;

import com.example.splitwise.Models.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Transaction {
    private int id;
    private User payer;
    private User receiver;
    private int amount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getPayer() {
        return payer;
    }

    public void setPayer(User payer) {
        this.payer = payer;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
