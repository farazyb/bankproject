package com.example.concurrent_banking_system.controller.states.acoount;

public record AccountCreationResponseDTO(String name, String accountNumber, double balance) {
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("name : ");
        stringBuilder.append(name).append("\n");
        stringBuilder.append(" account Number : ");
        stringBuilder.append(accountNumber).append("\n");;
        stringBuilder.append(" balance : ");
        stringBuilder.append(balance).append("\n");;
        return stringBuilder.toString();

    }
}
