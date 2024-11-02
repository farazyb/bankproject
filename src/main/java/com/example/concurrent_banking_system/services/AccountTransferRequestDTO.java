package com.example.concurrent_banking_system.services;

public record AccountTransferRequestDTO(String srcAccountNumber,String desAccountNumber,double amount) {
}
