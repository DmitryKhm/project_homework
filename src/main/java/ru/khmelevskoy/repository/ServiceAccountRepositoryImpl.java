package ru.khmelevskoy.repository;

import lombok.RequiredArgsConstructor;
import ru.khmelevskoy.entity.Account;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class ServiceAccountRepositoryImpl implements ServiceAccountRepositoryCustom{

    private final EntityManager em;

    @Override
    public List<Account> findByFilter(ServiceAccountFilter serviceAccountFilter) {

        String query = "select a from Account a where 1 = 1 ";

        Map<String, Object> params = new HashMap<>();
        if (serviceAccountFilter.getAccountNameLike() != null) {
            query += " and account_name like :accountName";
            params.put("accountName", serviceAccountFilter.getAccountNameLike());
        }
        if (serviceAccountFilter.getUserId() != null) {
            query += " and :id like user_id ";
            params.put("id", serviceAccountFilter.getUserId());
        }
        TypedQuery<Account> typedQuery= em.createQuery(query,Account.class);
        for(Map.Entry<String,Object> entry: params.entrySet()){
            typedQuery.setParameter(entry.getKey(),entry.getValue());
        }
        return typedQuery.getResultList();
    }
}