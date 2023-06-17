package com.educational.portal.domain.dto;

import com.educational.portal.validation.Iban;

import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Bank Account Request", description = "Dto class for requesting adding bank account")
public class AddBankAccountRequest {

    @Schema(description = "The IBAN (International Bank Account Number)", required = true, example = "DE89370400440532013000")
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
