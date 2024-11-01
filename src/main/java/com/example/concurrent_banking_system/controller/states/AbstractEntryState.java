package com.example.concurrent_banking_system.controller.states;

import java.util.Scanner;

public abstract class AbstractEntryState<T> implements EntryState<T> {
    protected  Scanner scanner ;

    public AbstractEntryState() {
        scanner = new Scanner(System.in);
    }

    protected AbstractEntryState<T> previousState;

    @Override
    public void previous(AbstractEntryState<T> previousState) {
        this.previousState = previousState;
    }
}
