package ru.khmelevskoy.repository;

import lombok.RequiredArgsConstructor;
import ru.khmelevskoy.entity.Transaction;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class ServiceTransactionRepositoryImpl implements ServiceTransactionRepositoryCustom {

    private final EntityManager em;

    @Override
    public List<Transaction> findByFilter(ServiceTransactionFilter filter) {

        String query = "select t from Transaction t where 1 = 1 ";

        Map<String, Object> params = new HashMap<>();
        if (filter.getTransactionId() != null) {
            query += " and id like :transactionId";
            params.put("transactionId", filter.getTransactionId());
        }
        if (filter.getTransactionValue() != null) {
            query += " and :value like transaction_value ";
            params.put("value", filter.getTransactionValue());
        }
        TypedQuery<Transaction> typedQuery = em.createQuery(query, Transaction.class);
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            typedQuery.setParameter(entry.getKey(), entry.getValue());
        }
        return typedQuery.getResultList();
    }
}