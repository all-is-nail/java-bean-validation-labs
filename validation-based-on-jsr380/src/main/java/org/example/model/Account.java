package org.example.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class Account {

    @NotNull
    private String accountNumber;
    @NotBlank(message = "Account holder name cannot be blank")
    private String accountHolderName;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }
}
