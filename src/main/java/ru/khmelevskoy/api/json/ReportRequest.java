package ru.khmelevskoy.api.json;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class ReportRequest {

    @NotEmpty
    String transactionDate;

    @NotNull
    @Positive
    long categoryId;
}