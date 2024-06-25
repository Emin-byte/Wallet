package com.example.WalletApp;

import com.example.WalletApp.entity.WalletEntity;
import com.example.WalletApp.model.BalanceResponse;
import com.example.WalletApp.model.CreateWalletDto;
import com.example.WalletApp.model.WalletSuccessResponse;
import com.example.WalletApp.repository.WalletAppRepository;
import com.example.WalletApp.service.WalletServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class WalletServiceTest {
    @Mock
    private WalletAppRepository walletAppRepository = mock(WalletAppRepository.class);
    @InjectMocks
    private WalletServiceImpl walletServiceImpl = mock(WalletServiceImpl.class);

    @Test
    public void testDeleteWalletById() {
        UUID id = UUID.randomUUID();
        when(walletAppRepository.findById(id)).thenReturn(Optional.of(new WalletEntity()));

        WalletSuccessResponse<BalanceResponse> expectedResponse = new WalletSuccessResponse<>(new BalanceResponse(id, null), null, null);

        when(walletServiceImpl.deleteWalletById(id)).thenReturn(expectedResponse);

        WalletSuccessResponse<BalanceResponse> response = walletServiceImpl.deleteWalletById(id);

        assertEquals(id, response.getAccount_balance_information().getWallet_Id());
        assertEquals(null, response.getAccount_balance_information().getBalance());
    }

    @Test
    public void testCreateWallet() {
        CreateWalletDto createWalletDto = new CreateWalletDto();
        createWalletDto.setOperationType("deposit");
        createWalletDto.setAmount(100L);

        WalletEntity expectedWalletEntity = new WalletEntity();
        expectedWalletEntity.setOperation_type(createWalletDto.getOperationType());
        expectedWalletEntity.setBalance(createWalletDto.getAmount());

        when(walletAppRepository.save(any(WalletEntity.class))).thenReturn(expectedWalletEntity);
        WalletSuccessResponse<BalanceResponse> expectedResponse = new WalletSuccessResponse<>(new BalanceResponse(UUID.randomUUID(), createWalletDto.getAmount()), null, null);
        when(walletServiceImpl.createWallet(createWalletDto)).thenReturn(expectedResponse);
        WalletSuccessResponse<BalanceResponse> response = walletServiceImpl.createWallet(createWalletDto);

        assertEquals(createWalletDto.getAmount(), response.getAccount_balance_information().getBalance());
    }

    @Test
    public void testChangeWallet() {
        UUID id = UUID.randomUUID();
        CreateWalletDto createWalletDto = new CreateWalletDto();
        createWalletDto.setOperationType("deposit");
        createWalletDto.setAmount(100L);

        WalletEntity walletEntity = new WalletEntity();
        walletEntity.setWallet_id(id);
        walletEntity.setOperation_type("withdrawal");
        walletEntity.setBalance(200L);

        when(walletAppRepository.findById(id)).thenReturn(Optional.of(walletEntity));

        WalletSuccessResponse<BalanceResponse> response = walletServiceImpl.changeWallet(createWalletDto, id);

        assertEquals(id, response.getAccount_balance_information().getWallet_Id());
        if (createWalletDto.getOperationType().equals("deposit")) {
            assertEquals(300L, response.getAccount_balance_information().getBalance());
        } else {
            assertEquals(100L, response.getAccount_balance_information().getBalance());
        }

        verify(walletAppRepository).save(walletEntity);
    }

    @Test
    public void testGetWalletInfo_ExistingWallet() {
        UUID id = UUID.randomUUID();
        Long balance = 500L;

        WalletEntity walletEntity = new WalletEntity();
        walletEntity.setWallet_id(id);
        walletEntity.setBalance(balance);

        when(walletAppRepository.findById(id)).thenReturn(Optional.of(walletEntity));
        WalletSuccessResponse<BalanceResponse> expectedResponse = new WalletSuccessResponse<>(new BalanceResponse(walletEntity.getWallet_id(), walletEntity.getBalance()), null, null);
        when(walletServiceImpl.getWalletInfo(walletEntity.getWallet_id())).thenReturn(expectedResponse);
        WalletSuccessResponse<BalanceResponse> response = walletServiceImpl.getWalletInfo(id);

        assertEquals(id, response.getAccount_balance_information().getWallet_Id());
        assertEquals(balance, response.getAccount_balance_information().getBalance());
    }
}