package com.example.concurrent_banking_system.configuration;

import com.example.concurrent_banking_system.controller.MenuContext;
import com.example.concurrent_banking_system.controller.states.MainMenuState;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class config {
    @Bean
    public MenuContext menuContext(MainMenuState mainMenuState) {

        return new MenuContext(mainMenuState);
    }

}
