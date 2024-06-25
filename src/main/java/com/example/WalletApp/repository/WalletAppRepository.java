package com.example.WalletApp.repository;

import com.example.WalletApp.entity.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WalletAppRepository extends JpaRepository<WalletEntity, UUID> {

}
