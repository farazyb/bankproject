package com.example.concurrent_banking_system.domain.entity;

import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.UUID;

public record AccountNumber(UUID accountNumber) implements Serializable {
    public AccountNumber {
        Assert.notNull(accountNumber, "must not be null");
    }

    public AccountNumber() {
        this(UUID.randomUUID());
    }
}
