package com.example.concurrent_banking_system.controller.states.acoount;

import com.example.concurrent_banking_system.controller.MenuContext;
import com.example.concurrent_banking_system.controller.states.AbstractEntryState;
import com.example.concurrent_banking_system.controller.states.EntryContext;
import com.example.concurrent_banking_system.controller.states.EntryState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class EnterNameState extends AbstractEntryState<AccountRequestDTO> {
    @Autowired
    EnterInitialBalanceState enterInitialBalanceState;


    protected void validateName(String name) {
        if (name == null || name.length() < 2) {
            throw new RuntimeException("Name is not valid. Please enter a valid name.");
        }
    }

    @Override
    public void execute(EntryContext<AccountRequestDTO> entryContext) {
        int choice = -1;  // Initialize choice with a default value
        while (true) {
            try {
                System.out.print("Enter your name: ");
                String name = scanner.next();
                validateName(name);
                entryContext.getContext().setName(name);
                choice = printMenu();
                switch (choice) {

                    case 1 -> {
                        enterInitialBalanceState.previous(this);
                        entryContext.setState(enterInitialBalanceState);
                    }
                    case 2 -> {

                    }
                    default -> System.out.println("invalid entry");
                }
                break;

            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        }
        if (choice == 2 && previousState == null) {
            entryContext.getMain().performAction();
        } else if (choice == 1 || previousState != null) {
            entryContext.execute();
        }
    }


    public int printMenu() {
        System.out.println("1. Next");
        System.out.println("2. Back");
        System.out.print("Choose an option: ");
        return scanner.nextInt();
    }
}
