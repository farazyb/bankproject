package com.example.concurrent_banking_system.controller.states.Withdraw;

public record WithdrawRequestDTO(String accountNumber, double amount) {
}
