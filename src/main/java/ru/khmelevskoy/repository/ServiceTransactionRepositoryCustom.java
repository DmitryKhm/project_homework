package ru.khmelevskoy.repository;

import ru.khmelevskoy.entity.Transaction;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface ServiceTransactionRepositoryCustom {
    List<Transaction> findByFilter(ServiceTransactionFilter serviceTransactionFilter);
}