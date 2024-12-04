package com.example.splitwise.Models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "Users") //always name table in PLURALS
public class User extends BaseModel{
    private String name;
    private String phoneNumber;
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
