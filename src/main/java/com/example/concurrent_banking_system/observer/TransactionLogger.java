package com.example.concurrent_banking_system.observer;


import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

@Component
public class TransactionLogger implements TransactionObserver {
    private static final String LOG_FILE_PATH = "transactions.log";

    @Override
    public void onTransaction(String accountNumber, String transactionType, double amount) {
        try (FileWriter fileWriter = new FileWriter(LOG_FILE_PATH, true);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {

            String logEntry = String.format("%s - Account: %s, Transaction: %s, Amount: %.2f",
                    LocalDateTime.now(), accountNumber, transactionType, amount);
            printWriter.println(logEntry);
        } catch (IOException e) {
            System.err.println("Error logging transaction: " + e.getMessage());
        }
    }
}
