package com.example.WalletApp.service;

import com.example.WalletApp.model.BalanceResponse;
import com.example.WalletApp.model.CreateWalletDto;
import com.example.WalletApp.model.WalletSuccessResponse;

import java.util.UUID;

public interface WalletService {
    public WalletSuccessResponse<BalanceResponse> createWallet(CreateWalletDto createWalletDto);

    public WalletSuccessResponse<BalanceResponse> changeWallet(CreateWalletDto createWalletDto, UUID id);

    public WalletSuccessResponse<BalanceResponse> getWalletInfo(UUID id);

    public WalletSuccessResponse<BalanceResponse> deleteWalletById(UUID id);

}
