package com.educational.portal.domain.dto;

import com.educational.portal.validation.Iban;

import javax.validation.constraints.NotBlank;

public class AddBankAccountRequest {
    @Iban
    @NotBlank(message = "Your bank account cannot be empty")
    private String iban;

    public AddBankAccountRequest() {
    }

    public AddBankAccountRequest(String iban) {
        this.iban = iban;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }
}
