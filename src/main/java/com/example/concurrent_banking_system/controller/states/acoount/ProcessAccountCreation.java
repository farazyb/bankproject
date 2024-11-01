package com.example.concurrent_banking_system.controller.states.acoount;

import com.example.concurrent_banking_system.controller.states.AbstractEntryState;
import com.example.concurrent_banking_system.controller.states.EntryContext;
import com.example.concurrent_banking_system.controller.states.MainMenuState;
import com.example.concurrent_banking_system.services.BankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProcessAccountCreation extends AbstractEntryState<AccountRequestDTO> {

    @Autowired
    BankingService bankingService;

    @Override
    public void execute(EntryContext<AccountRequestDTO> entryContext) {
        int choice = -1;
        while (true) {

            choice = confirmation(entryContext.getContext());
            switch (choice) {
                case 1 -> {
                    try {
                        AccountResponseDTO accountResponseDTO = process(entryContext.getContext());
                        printAccountDetails(accountResponseDTO);


                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        continue;
                    }

                }
                case 2 -> {
                }
                default -> System.out.println("invalid option");

            }
            break;
        }
        if (choice == 2) {
            entryContext.getMain().performAction();
        }


    }

    private AccountResponseDTO process(AccountRequestDTO accountRequestDTO) {
        return bankingService.create(accountRequestDTO);

    }

    public int confirmation(AccountRequestDTO accountRequestDTO) {
        System.out.println("please confirm below information");
        System.out.println(accountRequestDTO.toString());
        System.out.println("1. confirm ");
        System.out.println("2. Back to main");

        return scanner.nextInt();
    }

    public void printAccountDetails(AccountResponseDTO accountResponseDTO) {
        System.out.println(accountResponseDTO.toString());
    }


}
