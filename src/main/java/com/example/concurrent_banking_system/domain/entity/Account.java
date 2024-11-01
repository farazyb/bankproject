package com.example.concurrent_banking_system.domain.entity;

import jakarta.persistence.*;
import org.springframework.util.Assert;

@Entity
public class Account {
    @EmbeddedId
    private AccountNumber accountNumber;
    private String name;
    @Embedded
    @AttributeOverride(name = "balance", column = @Column(name = "balance"))
    private Balance balance;


    public Account(String name, Balance balance) {
        Assert.notNull(name, "name must not be null");
        Assert.notNull(balance, "balance must not be null");
        this.accountNumber = new AccountNumber();
        this.name = name;
        this.balance = balance;
    }

     Account() {

    }

    public String getAccountNumber() {
        return accountNumber.accountNumber().toString();
    }

    public String getName() {
        return name;
    }

    public Balance getBalance() {
        return balance;
    }
}
