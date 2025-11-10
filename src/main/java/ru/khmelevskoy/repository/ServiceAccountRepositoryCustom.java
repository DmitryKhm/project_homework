package ru.khmelevskoy.repository;

import ru.khmelevskoy.entity.Account;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface ServiceAccountRepositoryCustom {
    List<Account> findByFilter(ServiceAccountFilter serviceAccountFilter);
}