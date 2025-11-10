package ru.khmelevskoy.api.json;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class AccountResponse {
    private long id;

    private long userId;

    private BigDecimal value;

    private String accountName;
}