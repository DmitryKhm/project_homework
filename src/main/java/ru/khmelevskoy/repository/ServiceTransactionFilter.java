package ru.khmelevskoy.repository;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ServiceTransactionFilter {
    private Long transactionId;
    private Long transactionValue;
}