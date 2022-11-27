package com.educational.portal.domain.dto;

public class AddBankAccountRequest {

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
