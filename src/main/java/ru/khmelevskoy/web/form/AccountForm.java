package ru.khmelevskoy.web.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class AccountForm {

    @NotEmpty
    String accountName;

    @NotNull
    BigDecimal accountValue;
}