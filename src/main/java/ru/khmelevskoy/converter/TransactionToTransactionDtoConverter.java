package ru.khmelevskoy.converter;

import org.springframework.stereotype.Service;
import ru.khmelevskoy.dto.TransactionDTO;
import ru.khmelevskoy.entity.Transaction;

@Service
public class TransactionToTransactionDtoConverter implements Converter<Transaction, TransactionDTO> {

    @Override
    public TransactionDTO convert(Transaction source) {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setId(source.getId());
        transactionDTO.setAccountFrom(source.getAccountFrom().getId());
        transactionDTO.setAccountTo(source.getAccountTo().getId());
        transactionDTO.setValue(source.getValue());
        transactionDTO.setDate(source.getDate());
        return transactionDTO;
    }
}