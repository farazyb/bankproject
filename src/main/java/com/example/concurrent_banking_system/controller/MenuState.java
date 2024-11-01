package com.example.concurrent_banking_system.controller;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.util.List;


public interface MenuState {
    List<String> displayContent();

    void performAction(MenuContext menuContext, int choice);

    int getTotalMenuNumber();

    boolean isMainMenu();

    MenuState getPreviousState();

    public void setPreviousState(MenuState previousState);

}
