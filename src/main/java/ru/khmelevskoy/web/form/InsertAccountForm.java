package ru.khmelevskoy.web.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class InsertAccountForm {

    @NotEmpty
    String accountName;

    @NotNull
    Long accountValue;
}