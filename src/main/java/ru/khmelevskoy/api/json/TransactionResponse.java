package ru.khmelevskoy.api.json;

import lombok.AllArgsConstructor;
import lombok.Data;


import java.util.Date;

@Data
@AllArgsConstructor
public class TransactionResponse {
    long transactionId;
    long transactionValue;
    long toAccId;
    long fromAccId;
    Date transactionDate;
}