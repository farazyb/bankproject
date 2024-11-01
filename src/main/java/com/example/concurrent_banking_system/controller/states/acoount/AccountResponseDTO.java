package com.example.concurrent_banking_system.controller.states.acoount;

import java.util.StringJoiner;

public record AccountResponseDTO(String name, String accountNumber, double balance) {
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("name : ");
        stringBuilder.append(name);
        stringBuilder.append(" account Number : ");
        stringBuilder.append(accountNumber);
        stringBuilder.append(" balance : ");
        stringBuilder.append(balance);
        return stringBuilder.toString();

    }
}
