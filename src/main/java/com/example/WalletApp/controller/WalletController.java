package com.example.WalletApp.controller;

import com.example.WalletApp.model.BalanceResponse;
import com.example.WalletApp.model.CreateWalletDto;
import com.example.WalletApp.model.WalletSuccessResponse;
import com.example.WalletApp.service.WalletService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("api/v1/wallet")
@RequiredArgsConstructor
@Validated
public class WalletController {
    private final WalletService walletService;

    @PostMapping()
    public ResponseEntity<WalletSuccessResponse> registerUser(
            @RequestBody @Valid CreateWalletDto createWalletDto) {
        return ResponseEntity.ok(walletService.createWallet(createWalletDto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<WalletSuccessResponse<BalanceResponse>> changeWallet(
            @PathVariable @NotNull(message = "введите положительное число ")
            String id, @RequestBody CreateWalletDto createWalletDto) {
        return ResponseEntity.ok(walletService.changeWallet(createWalletDto, UUID.fromString(id)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<WalletSuccessResponse<BalanceResponse>> getWalletInfo(
            @PathVariable @NotNull(message = "введите положительное число ")
            String id) {
        return ResponseEntity.ok(walletService.getWalletInfo(UUID.fromString(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<WalletSuccessResponse<BalanceResponse>> deleteWalletById(
            @PathVariable @NotNull(message = "введите положительное число ")
            String id) {
        return ResponseEntity.ok(walletService.deleteWalletById(UUID.fromString(id)));

    }
}
