package com.example.concurrent_banking_system.controller.states.acoount;

public class AccountRequestDTO {
    private String name;
    private double initBalance;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getInitBalance() {
        return initBalance;
    }

    public void setInitBalance(double initBalance) {
        this.initBalance = initBalance;
    }

    @Override
    public String toString() {
        return
                "name= " + name +
                        ", initBalance=" + initBalance;
    }
}
