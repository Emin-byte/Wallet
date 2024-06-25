package com.example.WalletApp.service;

import com.example.WalletApp.entity.WalletEntity;
import com.example.WalletApp.errors.WalletExceptions;
import com.example.WalletApp.model.BalanceResponse;
import com.example.WalletApp.model.CreateWalletDto;
import com.example.WalletApp.model.WalletSuccessResponse;
import com.example.WalletApp.repository.WalletAppRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService{

    private final WalletAppRepository walletAppRepository;

@Transactional
    @Override
    public WalletSuccessResponse<BalanceResponse> createWallet(CreateWalletDto createWalletDto) {
        WalletEntity walletEntity = new WalletEntity();
                walletEntity.setOperation_type(createWalletDto.getOperationType());
                    walletEntity.setBalance(createWalletDto.getAmount());
       walletAppRepository.save(walletEntity);
       return new WalletSuccessResponse<>(new BalanceResponse(walletEntity.getWallet_id(), walletEntity.getBalance()),null,null);
    }

    @Override
    public WalletSuccessResponse<BalanceResponse> changeWallet(CreateWalletDto createWalletDto, UUID id) {
        Optional<WalletEntity> walletEntity = walletAppRepository.findById(id);
        walletEntity.get().setOperation_type(createWalletDto.getOperationType());
        if (createWalletDto.getOperationType().equals("deposit")){
            walletEntity.get().setBalance(walletEntity.get().getBalance()+createWalletDto.getAmount() );
        }else {
            walletEntity.get().setBalance(walletEntity.get().getBalance() - createWalletDto.getAmount());
        }
        walletAppRepository.save(walletEntity.get());
        return new WalletSuccessResponse<>(new BalanceResponse(id, walletEntity.get().getBalance()),null,null);
    }

    @Override
    public WalletSuccessResponse<BalanceResponse> getWalletInfo(UUID id) {
        Optional<WalletEntity> walletEntity = walletAppRepository.findById(id);
        if (walletEntity.isEmpty()){
            throw new WalletExceptions("счет не существует");
        }
        return new WalletSuccessResponse<>(new BalanceResponse(id, walletEntity.get().getBalance()),null,null);
    }

    @Override
    public WalletSuccessResponse<BalanceResponse> deleteWalletById(UUID id) {
         walletAppRepository.findById(id).orElseThrow(()->new WalletExceptions("счет не существует"));
       walletAppRepository.deleteById(id);
        return new WalletSuccessResponse<>(new BalanceResponse(id, null), null,null);
    }
}
