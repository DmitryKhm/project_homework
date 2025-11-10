package ru.khmelevskoy.api.json;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
public class AccountRequest {

    @NotNull
    @Positive
    private Long userId;

    private String accountName;

    @NotNull
    private BigDecimal accountValue;
}