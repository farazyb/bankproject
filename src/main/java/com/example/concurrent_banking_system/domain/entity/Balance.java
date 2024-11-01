package com.example.concurrent_banking_system.domain.entity;

import org.springframework.util.Assert;

public record Balance(double balance) {
    public Balance {
        Assert.isTrue(balance>0,"balance must be bigger than zero");
    }

}
