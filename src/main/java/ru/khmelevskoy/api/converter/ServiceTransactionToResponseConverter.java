package ru.khmelevskoy.api.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.khmelevskoy.api.json.TransactionResponse;
import ru.khmelevskoy.dto.TransactionDTO;

@Component
public class ServiceTransactionToResponseConverter implements Converter<TransactionDTO, TransactionResponse> {

    @Override
    public TransactionResponse convert(TransactionDTO transaction) {
        return new TransactionResponse(transaction.getId(), transaction.getValue(), transaction.getAccountTo(),
                transaction.getAccountFrom(), transaction.getDate());
    }
}