package com.example.WalletApp.model;

import com.example.WalletApp.enam.OperationType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NonNull;

@Data
public class CreateWalletDto {
    @NotNull(message = "Требуется ввод типа операции")
    private String operationType;
    @Positive(message = "Требуется ввод положительного значения")
    @NotNull(message = "Укажите количество")
    private Long amount;
}
