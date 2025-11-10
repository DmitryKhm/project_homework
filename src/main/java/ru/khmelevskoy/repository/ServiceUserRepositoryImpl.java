package ru.khmelevskoy.repository;

import lombok.RequiredArgsConstructor;
import ru.khmelevskoy.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class ServiceUserRepositoryImpl implements ServiceUserRepositoryCustom {

    private final EntityManager em;

    @Override
    public List<User> findByFilter(ServiceUserFilter userFilter) {

        String query = "select u from User u where 1 = 1 ";

        Map<String, Object> params = new HashMap<>();
        if (userFilter.getEmailLike() != null) {
            query += " and email like :emailLike";
            params.put("emailLike", userFilter.getEmailLike());
        }
        if (userFilter.getUserRole() != null) {
            query += " and :role member of u.roles ";
            params.put("role", userFilter.getUserRole());
        }
        TypedQuery<User> typedQuery = em.createQuery(query, User.class);
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            typedQuery.setParameter(entry.getKey(), entry.getValue());
        }
        return typedQuery.getResultList();
    }
}