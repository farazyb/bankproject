package com.example.concurrent_banking_system.controller.states.acoount;

import com.example.concurrent_banking_system.controller.states.AbstractEntryState;
import com.example.concurrent_banking_system.controller.states.EntryContext;
import org.springframework.stereotype.Component;

@Component
public class EnterInitialBalanceState extends AbstractEntryState<AccountCreationRequestDTO> {
    final
    ProcessAccountCreation processAccountCreation;

    public EnterInitialBalanceState(ProcessAccountCreation processAccountCreation) {
        this.processAccountCreation = processAccountCreation;
    }

    @Override
    public void execute(EntryContext<AccountCreationRequestDTO> entryContext) {
        while (true) {
            System.out.print("Enter initial balance: ");
            try {
                double balance = Double.parseDouble(scanner.next());
                validateInitialBalance(balance);
                AccountCreationRequestDTO accountRequestDTO = entryContext.getContext();
                accountRequestDTO.setInitBalance(balance);
                int choice = printMenu();
                switch (choice) {
                    case 1 -> {
                        processAccountCreation.previous(this);
                        entryContext.setState(processAccountCreation);
                    }
                    case 2 -> {
                        entryContext.setState(previousState);
                    }
                    default -> {
                        System.out.println("invalid option");
                    }
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid balance. Please enter a valid number.");
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }

        }
        entryContext.execute();
    }


    protected void validateInitialBalance(double balance) {
        if (balance < 0) {
            throw new RuntimeException("Initial balance must be non-negative.");
        }
    }

    public int printMenu() {
        System.out.println("1. Next");
        System.out.println("2. Back");
        System.out.print("Choose an option: ");
        return scanner.nextInt();
    }
}
