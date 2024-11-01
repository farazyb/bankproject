package com.example.concurrent_banking_system.controller;

import com.example.concurrent_banking_system.controller.states.MainMenuState;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class MenuContext {
    private MenuState currentMenuState;
    private int backChoice;
    private final MainMenuState mainMenuState;
    private final Scanner scanner;

    public MenuContext(MainMenuState mainMenuState) {
        this.mainMenuState = mainMenuState;
        this.currentMenuState = this.mainMenuState;
        this.scanner = new Scanner(System.in);
        updateBackChoice();
    }

    public void setState(MenuState currentMenuState) {
        this.currentMenuState = currentMenuState;
        updateBackChoice();
    }

    private void updateBackChoice() {
        this.backChoice = currentMenuState.getTotalMenuNumber() + 1;
    }

    private String getBackOptionText() {
        return currentMenuState.isMainMenu() ? backChoice + ". Exit" : backChoice + ". Back";
    }

    private int getUserChoice() {
        System.out.print("Choose an option: ");
        return scanner.nextInt();
    }

    public void performAction() {
        List<String> contents = currentMenuState.displayContent();
        contents.add(getBackOptionText());
        printInFixedBox(contents);

        int choice = getUserChoice();
        if (choice == backChoice) {
            handleBackOrExit();
        } else {
            currentMenuState.performAction(this, choice);
        }
    }

    private void handleBackOrExit() {
        if (currentMenuState.isMainMenu()) {
            System.out.println("Have a good day, bye");
        } else {
            setState(currentMenuState.getPreviousState());
            performAction();  // Re-display menu after state change
        }
    }

    public static void printInFixedBox(List<String> contentList) {
        String border = "-".repeat(41);
        System.out.println(border);
        for (String content : contentList) {
            List<String> splitLines = splitStringByLength(content, 37);
            for (String line : splitLines) {
                System.out.printf("| %-37s |\n", line);
            }
        }
        System.out.println(border);
    }

    public static List<String> splitStringByLength(String str, int maxLength) {
        List<String> lines = new ArrayList<>();
        int start = 0;
        while (start < str.length()) {
            int end = Math.min(start + maxLength, str.length());
            lines.add(str.substring(start, end));
            start += maxLength;
        }
        return lines;
    }
}
