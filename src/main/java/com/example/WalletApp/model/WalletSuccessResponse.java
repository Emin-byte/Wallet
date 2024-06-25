package com.example.WalletApp.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WalletSuccessResponse<T> {
    public T account_balance_information;
    private Boolean succsess = true;
    private List<String> errorMessage;

    public static <T> WalletSuccessResponse<T> getBadRequest(List<String> errorMessage) {
        return new WalletSuccessResponse<T>(null, false, errorMessage);
    }
}
