package com.example.concurrent_banking_system.services;

import com.example.concurrent_banking_system.controller.states.Withdraw.WithdrawRequestDTO;
import com.example.concurrent_banking_system.controller.states.Withdraw.WithdrawResponseDTO;
import com.example.concurrent_banking_system.controller.states.acoount.AccountCreationRequestDTO;
import com.example.concurrent_banking_system.controller.states.acoount.AccountCreationResponseDTO;
import com.example.concurrent_banking_system.controller.states.balance.AccountBalanceRequestDTO;
import com.example.concurrent_banking_system.controller.states.balance.AccountBalanceResponseDTO;
import com.example.concurrent_banking_system.controller.states.deposit.AccountDepositRequestDTO;
import com.example.concurrent_banking_system.controller.states.deposit.AccountDepositResponseDTO;
import com.example.concurrent_banking_system.domain.entity.Account;
import com.example.concurrent_banking_system.domain.entity.AccountNumber;
import com.example.concurrent_banking_system.domain.entity.Balance;
import com.example.concurrent_banking_system.domain.repository.AccountRepository;
import com.example.concurrent_banking_system.observer.TransactionLogger;
import com.example.concurrent_banking_system.observer.TransactionObserver;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.*;

@Service

public class BankingService implements BankingServiceInterface {


    private final AccountRepository accountRepository;
    private final ExecutorService executorService;
    private final List<TransactionObserver> observers = new ArrayList<>();

    public BankingService(AccountRepository accountRepository, TransactionLogger transactionLogger) {
        this.accountRepository = accountRepository;
        executorService = Executors.newFixedThreadPool(10); // Configurable pool size
        registerObserver(transactionLogger);
    }

    public void registerObserver(TransactionObserver observer) {
        observers.add(observer);
    }

    // Method to notify all observers
    private void notifyObservers(String accountNumber, String transactionType, double amount) {
        for (TransactionObserver observer : observers) {
            observer.onTransaction(accountNumber, transactionType, amount);
        }
    }


    public CompletableFuture<AccountCreationResponseDTO> create(AccountCreationRequestDTO accountRequestDTO) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Optional<Account> existingAccount = accountRepository.findAccountByName(accountRequestDTO.getName());
                if (existingAccount.isPresent()) {
                    throw new RuntimeException("Account Already Registered");
                }

                Account account = new Account(accountRequestDTO.getName(), new Balance(accountRequestDTO.getInitBalance()));
                TimeUnit.SECONDS.sleep(3); // Simulate delay
                account = accountRepository.save(account);

                // Notify observers about the creation transaction
                notifyObservers(account.getAccountNumber().toString(), "CREATION", account.getBalance().balance());

                return new AccountCreationResponseDTO(account.getName(), account.getAccountNumber(), account.getBalance().balance());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Thread was interrupted", e);
            }
        }, executorService);
    }


    @Override
    public CompletableFuture<AccountBalanceResponseDTO> balance(AccountBalanceRequestDTO accountBalanceRequestDTO) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Optional<Account> account = accountRepository.findAccountByAccountNumber(new AccountNumber(UUID.fromString(accountBalanceRequestDTO.accountNumber())));

                TimeUnit.SECONDS.sleep(3); // Simulate delay

                // Notify observers about the balance check transaction
                account.ifPresent(acc -> notifyObservers(acc.getAccountNumber().toString(), "BALANCE_CHECK", 0.0));

                return account.map(acc -> new AccountBalanceResponseDTO(acc.getName(), acc.getAccountNumber(), acc.getBalance().balance())).orElseThrow(() -> new RuntimeException("Account not found"));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Thread was interrupted", e);
            } catch (Exception e) {
                throw new RuntimeException("An error occurred while retrieving the account balance", e);
            }
        }, executorService);
    }

    public CompletableFuture<AccountDepositResponseDTO> deposit(AccountDepositRequestDTO accountDepositRequestDTO) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Account account = accountRepository.findAccountByAccountNumber(new AccountNumber(UUID.fromString(accountDepositRequestDTO.accountNumber()))).orElseThrow(() -> new RuntimeException("Account not found"));
                TimeUnit.SECONDS.sleep(3); // Simulate delay
                account = accountRepository.save(new Account(account.getName(), account.getBalance().add(accountDepositRequestDTO.amount())));

                // Notify observers about the deposit transaction
                notifyObservers(accountDepositRequestDTO.accountNumber(), "DEPOSIT", accountDepositRequestDTO.amount());

                return new AccountDepositResponseDTO(account.getName(), account.getAccountNumber(), account.getBalance().balance());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Thread was interrupted", e);
            }
        }, executorService);
    }

    public CompletableFuture<WithdrawResponseDTO> withdraw(WithdrawRequestDTO withdrawResponseDTO) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Account account = accountRepository.findAccountByAccountNumber(new AccountNumber(UUID.fromString(withdrawResponseDTO.accountNumber()))).orElseThrow(() -> new RuntimeException("Account not found"));
                TimeUnit.SECONDS.sleep(3); // Simulate delay
                if (account.getBalance().balance() < withdrawResponseDTO.amount()) {
                    throw new RuntimeException("Insufficient balance");
                }

                account = accountRepository.save(new Account(account.getName(), account.getBalance().subtract(withdrawResponseDTO.amount())));

                // Notify observers about the withdrawal transaction
                notifyObservers(withdrawResponseDTO.accountNumber(), "WITHDRAWAL", withdrawResponseDTO.amount());

                return new WithdrawResponseDTO(account.getName(), account.getAccountNumber(), account.getBalance().balance());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Thread was interrupted", e);
            }
        }, executorService);
    }

    public CompletableFuture<AccountTransferResponseDTO> transfer(AccountTransferRequestDTO accountTransferRequestDTO) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                // Retrieve the source and destination accounts
                Account fromAccount = accountRepository.findAccountByAccountNumber(new AccountNumber(UUID.fromString(accountTransferRequestDTO.srcAccountNumber()))).orElseThrow(() -> new RuntimeException("Source account not found"));

                Account toAccount = accountRepository.findAccountByAccountNumber(new AccountNumber(UUID.fromString(accountTransferRequestDTO.desAccountNumber()))).orElseThrow(() -> new RuntimeException("Destination account not found"));
                TimeUnit.SECONDS.sleep(3);
                // Check if the source account has enough balance
                if (fromAccount.getBalance().balance() < accountTransferRequestDTO.amount()) {
                    throw new RuntimeException("Insufficient balance in source account");
                }

                // Perform the transfer by subtracting from the source and adding to the destination
                fromAccount = accountRepository.save(new Account(fromAccount.getName(), fromAccount.getBalance().subtract(accountTransferRequestDTO.amount())));

                toAccount = accountRepository.save(new Account(toAccount.getName(), toAccount.getBalance().add(accountTransferRequestDTO.amount())));

                // Notify observers about the transfer transaction
                notifyObservers(accountTransferRequestDTO.srcAccountNumber(), "TRANSFER_OUT", accountTransferRequestDTO.amount());
                notifyObservers(accountTransferRequestDTO.desAccountNumber(), "TRANSFER_IN", accountTransferRequestDTO.amount());
                return new AccountTransferResponseDTO(fromAccount.getName(), fromAccount.getAccountNumber(), fromAccount.getBalance().balance());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Thread was interrupted", e);
            }
        }, executorService);
    }


}
