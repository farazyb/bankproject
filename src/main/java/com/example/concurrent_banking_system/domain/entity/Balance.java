package com.example.concurrent_banking_system.domain.entity;

import org.springframework.util.Assert;

public record Balance(double balance) {
    public Balance {
        Assert.isTrue(balance > 0, "balance must be bigger than zero");
    }

    public Balance subtract(double amount) {
        Assert.isTrue(amount >= 0, "Amount to subtract must be zero or greater");
        double newBalance = balance - amount;
        Assert.isTrue(newBalance >= 0, "Resulting balance must be zero or greater");
        return new Balance(newBalance);
    }

    public Balance add(double amount) {
        Assert.isTrue(amount >= 0, "Amount to add must be zero or greater");
        return new Balance(balance + amount);
    }
}
