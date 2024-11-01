package com.example.concurrent_banking_system.services;

import com.example.concurrent_banking_system.controller.states.acoount.AccountRequestDTO;
import com.example.concurrent_banking_system.controller.states.acoount.AccountResponseDTO;
import com.example.concurrent_banking_system.domain.entity.Account;
import com.example.concurrent_banking_system.domain.entity.Balance;
import com.example.concurrent_banking_system.domain.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankingService {

    @Autowired
    AccountRepository accountRepository;

    public AccountResponseDTO create(AccountRequestDTO accountRequestDTO) {
        Account account = new Account(accountRequestDTO.getName(), new Balance(accountRequestDTO.getInitBalance()));
        account = accountRepository.save(account);
        return new AccountResponseDTO(account.getName(), account.getAccountNumber(), account.getBalance().balance());
    }

}
