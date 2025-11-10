package ru.khmelevskoy.web.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class TransactionsForm {

    @NotNull
    Long accountId;
}