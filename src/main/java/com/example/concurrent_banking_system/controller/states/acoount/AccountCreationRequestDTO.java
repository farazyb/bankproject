package com.example.concurrent_banking_system.controller.states.acoount;

import lombok.Data;

@Data
public class AccountCreationRequestDTO {
    private String name;
    private double initBalance;


    @Override
    public String toString() {
        return
                "name= " + name +
                        ", initBalance=" + initBalance;
    }
}
