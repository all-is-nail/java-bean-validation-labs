package org.example.model;

import javax.validation.constraints.NotNull;

public class Account {

    @NotNull
    private String accountNumber;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}
