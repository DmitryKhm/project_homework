package ru.khmelevskoy.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.khmelevskoy.converter.TransactionToTransactionDtoConverter;
import ru.khmelevskoy.dto.TransactionDTO;
import ru.khmelevskoy.entity.Account;
import ru.khmelevskoy.entity.Category;
import ru.khmelevskoy.entity.Transaction;
import ru.khmelevskoy.repository.ServiceAccountRepository;
import ru.khmelevskoy.repository.ServiceCategoryRepository;
import ru.khmelevskoy.repository.ServiceTransactionRepository;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServiceTransaction {

    private final ServiceAccountRepository accountRepository;

    private final ServiceTransactionRepository transactionRepository;

    private final ServiceCategoryRepository categoryRepository;

    private final TransactionToTransactionDtoConverter converter;

    public List<TransactionDTO> getTransactions(Long userId, Long accountId) {
        Account account = accountRepository.getAccountByIdAndUserId(accountId, userId);
        return transactionRepository.getAllByAccountFromOrAccountTo(account).stream()
                .map(converter::convert)
                .collect(Collectors.toList());
    }

    public Transaction findTransaction(Long transactionId) {
        return transactionRepository.getById(transactionId);
    }

    public List<TransactionDTO> findReport(Long userId, Long categoryId, Date date) {
        Category category = categoryRepository.getByIdAndAndUserId(categoryId, userId);
        return transactionRepository.getAllByDateAndCategory(date, category).stream()
                .map(converter::convert)
                .collect(Collectors.toList());
    }

    public TransactionDTO createTransaction(Long toAccId, Long fromAccId, Long transactionValue, Date date) {
        Account accTo = accountRepository.getById(toAccId);
        Account from = accountRepository.getById(fromAccId);
        Transaction transaction = new Transaction();
        transaction.setAccountTo(accTo);
        transaction.setAccountFrom(from);
        transaction.setValue(transactionValue);
        transaction.setDate(date);
        long transactionId = transactionRepository.save(transaction).getId();
        if (findTransaction(transactionId) != null) {
            return converter.convert(transaction);
        }
        return null;
    }
}