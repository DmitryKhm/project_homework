package ru.khmelevskoy.api.json;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class TransactionRequest {

    @NotNull
    @Positive
    long transactionValue;

    @NotNull
    @Positive
    long toAccId;

    @NotNull
    @Positive
    long fromAccId;

    @NotEmpty
    String transactionDate;

    @NotNull
    @Positive
    long categoryId;
}