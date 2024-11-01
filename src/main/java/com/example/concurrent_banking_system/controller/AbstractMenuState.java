package com.example.concurrent_banking_system.controller;

public abstract class AbstractMenuState implements MenuState {
    protected MenuState previousState;

    @Override
    public MenuState getPreviousState() {
        return this.previousState;
    }

    @Override
    public void setPreviousState(MenuState previousState) {
        this.previousState = previousState;
    }
}
