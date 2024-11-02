package com.example.concurrent_banking_system.observer;

public interface TransactionObserver {
    void onTransaction(String accountNumber, String transactionType, double amount);
}
