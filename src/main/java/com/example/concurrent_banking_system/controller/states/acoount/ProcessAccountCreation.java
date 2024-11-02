package com.example.concurrent_banking_system.controller.states.acoount;

import com.example.concurrent_banking_system.controller.states.AbstractEntryState;
import com.example.concurrent_banking_system.controller.states.EntryContext;
import com.example.concurrent_banking_system.services.BankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Component
public class ProcessAccountCreation extends AbstractEntryState<AccountCreationRequestDTO> {

    @Autowired
    BankingService bankingService;

    @Override
    public void execute(EntryContext<AccountCreationRequestDTO> entryContext) {
        int choice = -1;
        while (true) {

            choice = confirmation(entryContext.getContext());
            switch (choice) {
                case 1 -> {
                    try {
                        AccountCreationResponseDTO accountResponseDTO = process(entryContext.getContext());
                        printAccountDetails(accountResponseDTO);
                        choice=2;
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

    public AccountCreationResponseDTO process(AccountCreationRequestDTO accountRequestDTO) {
        CompletableFuture<AccountCreationResponseDTO> futureResponse = bankingService.create(accountRequestDTO);

        // Show a spinner until the future is completed
        showSpinner(futureResponse);

        // Retrieve the result after completion
        return futureResponse.join(); // Use join() to block until the future completes
    }

    private void showSpinner(CompletableFuture<AccountCreationResponseDTO> futureResponse) {
        String[] spinnerFrames = {"-", "\\", "|", "/"};
        int frameIndex = 0;

        while (!futureResponse.isDone()) {
            System.out.print("\b" + spinnerFrames[frameIndex % spinnerFrames.length]); // Overwrite previous spinner character
            frameIndex++;

            try {
                TimeUnit.MILLISECONDS.sleep(200); // Adjust sleep time for spinner speed
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Reset interrupt flag
                throw new RuntimeException("Spinner interrupted", e);
            }
        }
        clearScreen(); // Clean up the spinner from the console
    }

    public static void clearScreen() {
        System.out.flush();
    }

    public int confirmation(AccountCreationRequestDTO accountRequestDTO) {
        System.out.println("please confirm below information");
        System.out.println(accountRequestDTO.toString());
        System.out.println("1. confirm ");
        System.out.println("2. Back to main");

        return scanner.nextInt();
    }

    public void printAccountDetails(AccountCreationResponseDTO accountResponseDTO) {
        System.out.println("Account created successfully");
        System.out.println("Your Information : ");
        System.out.println(accountResponseDTO.toString());
    }


}
