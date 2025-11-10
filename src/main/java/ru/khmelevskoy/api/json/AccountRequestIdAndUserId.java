package ru.khmelevskoy.api.json;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class AccountRequestIdAndUserId {

    @NotNull
    @Positive
    private Long userId;

    @NotNull
    @Positive
    private Long accountId;
}