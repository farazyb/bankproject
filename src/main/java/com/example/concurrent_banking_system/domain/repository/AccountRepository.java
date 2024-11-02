package com.example.concurrent_banking_system.domain.repository;

import com.example.concurrent_banking_system.domain.entity.Account;
import com.example.concurrent_banking_system.domain.entity.AccountNumber;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AccountRepository extends CrudRepository<Account, AccountNumber> {

    Optional<Account> findAccountByName(String name);

    Optional<Account> findAccountByAccountNumber(AccountNumber accountNumber);
}
