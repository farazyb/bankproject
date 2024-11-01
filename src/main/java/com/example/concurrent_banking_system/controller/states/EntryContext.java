package com.example.concurrent_banking_system.controller.states;

import com.example.concurrent_banking_system.controller.AbstractMenuState;
import com.example.concurrent_banking_system.controller.MenuContext;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class EntryContext<T> {
    private AbstractEntryState<T> currentlyState;
    private T context;
    private MenuContext main;

    public EntryContext() {
    }

    public void init(T context, MenuContext main) {
        this.context = context;
        this.main = main;

    }

    public void setState(AbstractEntryState<T> nextState) {
        this.currentlyState = nextState;
    }


    public void execute() {
        currentlyState.execute(this);

    }

    public T getContext() {
        return context;
    }

    public MenuContext getMain() {
        return main;
    }
}
