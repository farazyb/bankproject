package com.example.concurrent_banking_system.controller.states.acoount;

import com.example.concurrent_banking_system.controller.AbstractMenuState;
import com.example.concurrent_banking_system.controller.EntryMode;
import com.example.concurrent_banking_system.controller.MenuContext;
import com.example.concurrent_banking_system.controller.states.EntryContext;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CreateAccountState extends AbstractMenuState implements EntryMode {
    final EnterNameState enterNameState;
    final ObjectFactory<EntryContext<AccountRequestDTO>> entryContextObjectFactory;
    private MenuContext menuContext;

    public CreateAccountState(EnterNameState enterNameState, ObjectFactory<EntryContext<AccountRequestDTO>> entryContextObjectFactory) {
        this.enterNameState = enterNameState;
        this.entryContextObjectFactory = entryContextObjectFactory;
    }

    @Override
    public int getTotalMenuNumber() {
        return 1;
    }

    @Override
    public List<String> displayContent() {
        return new ArrayList<>(List.of("Welcome to Foo Bank ", "1. Next"));
    }

    @Override
    public void performAction(MenuContext menuContext, int choice) {
        if (choice == 1) {
            this.menuContext = menuContext;
            goToEntryState();
        } else {
            menuContext.performAction();
        }
    }

    @Override
    public boolean isMainMenu() {
        return false;
    }


    @Override
    public void goToEntryState() {
        EntryContext<AccountRequestDTO> entryContext = entryContextObjectFactory.getObject();
        menuContext.setState(previousState);
        entryContext.init(new AccountRequestDTO(),menuContext);
        enterNameState.previous(null);
        entryContext.setState(enterNameState);
        entryContext.execute();
    }
}
