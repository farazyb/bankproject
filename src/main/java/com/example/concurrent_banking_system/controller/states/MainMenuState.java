package com.example.concurrent_banking_system.controller.states;

import com.example.concurrent_banking_system.controller.AbstractMenuState;
import com.example.concurrent_banking_system.controller.MenuContext;
import com.example.concurrent_banking_system.controller.MenuState;
import com.example.concurrent_banking_system.controller.states.acoount.CreateAccountState;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MainMenuState extends AbstractMenuState {
    //    final
//    BalanceState balanceState;
//    final TransferState transferState;
    final CreateAccountState createAccountState;
//    final WithdrawalState withdrawalState;


    public MainMenuState(CreateAccountState createAccountState) {
        this.createAccountState = createAccountState;
    }

    @Override
    public List<String> displayContent() {
//        System.out.println("Main Menu:");
//        System.out.println("1. Check Balance");
//        System.out.println("2. Transfer Card to Card");
//        System.out.println("3. Create Account");
//        System.out.println("4. withdrawal");
        return new ArrayList<>(List.of("Main Menu:", "1. Check Balance", "2. Transfer Card to Card", "3. Create Account", "4. withdrawal"));
    }

    @Override
    public void performAction(MenuContext menuContext, int choice) {
        while (true) {
            switch (choice) {
//                case 1 -> menuContext.setState(balanceState, this);
//                case 2 -> menuContext.setState(transferState, this);
                case 3 -> {
                    createAccountState.setPreviousState(this);
                    menuContext.setState(createAccountState);
                }
//                case 4 -> menuContext.setState(withdrawalState, this);
                default -> {
                    System.out.println("Invalid option. Returning to main menu.");

                }
            }
            break;
        }
        menuContext.performAction();

    }

    @Override
    public int getTotalMenuNumber() {
        return 4;
    }

    @Override
    public boolean isMainMenu() {
        return true;
    }

    @Override
    public MenuState getPreviousState() {
        return null;
    }


}
