package com.example.concurrent_banking_system.services;

import com.example.concurrent_banking_system.controller.states.acoount.AccountCreationRequestDTO;
import com.example.concurrent_banking_system.controller.states.acoount.AccountCreationResponseDTO;
import com.example.concurrent_banking_system.controller.states.balance.AccountBalanceRequestDTO;
import com.example.concurrent_banking_system.controller.states.balance.AccountBalanceResponseDTO;
import com.example.concurrent_banking_system.domain.entity.Account;
import com.example.concurrent_banking_system.domain.entity.AccountNumber;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface BankingServiceInterface {

    CompletableFuture<AccountCreationResponseDTO> create(AccountCreationRequestDTO accountCreationRequestDTO);

    CompletableFuture<AccountBalanceResponseDTO> balance(AccountBalanceRequestDTO accountBalanceRequestDTO);

}
