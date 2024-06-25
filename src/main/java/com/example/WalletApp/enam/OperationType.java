package com.example.WalletApp.enam;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public enum OperationType {
    DEPOSIT("deposit"),
    WITHDRAW("withdraw");

    public static final Map<String, OperationType> operationTypes;
    private final String operationType;

    static {
        Map<String, OperationType> operationTypeMap = new HashMap<>();
        for (OperationType operationType1 : values()) {
            operationTypeMap.put(operationType1.operationType, operationType1);
        }
        operationTypes = Collections.unmodifiableMap(operationTypeMap);
    }


}
