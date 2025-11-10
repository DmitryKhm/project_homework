package ru.khmelevskoy.web.form;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class TransactionForm {

    @NotNull
    Long accountFrom;

    @NotNull
    Long accountTo;

    @NotNull
    Date date;
}