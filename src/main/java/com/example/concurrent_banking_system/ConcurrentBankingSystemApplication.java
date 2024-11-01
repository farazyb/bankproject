package com.example.concurrent_banking_system;

import com.example.concurrent_banking_system.controller.MenuContext;
import com.example.concurrent_banking_system.domain.entity.Account;
import com.example.concurrent_banking_system.domain.entity.Balance;
import com.example.concurrent_banking_system.domain.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConcurrentBankingSystemApplication implements CommandLineRunner {
    @Autowired
    MenuContext menuContext;

    public static void main(String[] args) {
        SpringApplication.run(ConcurrentBankingSystemApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        menuContext.performAction();
    }

}
