package com.example.WalletApp.entity;

import com.example.WalletApp.enam.OperationType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Entity
@Data
public class WalletEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID wallet_id;
    private String operation_type;
    private Long balance;
}
