package ru.khmelevskoy.repository;

import lombok.RequiredArgsConstructor;
import ru.khmelevskoy.entity.Category;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RequiredArgsConstructor
public class ServiceCategoryRepositoryImpl implements ServiceCategoryRepositoryCustom {

    private final EntityManager em;

    @Override
    public List<Category> findByFilter(ServiceCategoryFilter serviceCategoryFilter) {
        String query = "select c from Category c where 1 = 1 ";

        Map<String, Object> params = new HashMap<>();
        if (serviceCategoryFilter.getCategoryGroupLike() != null) {
            query += " and category_group like :groupName";
            params.put("groupName", serviceCategoryFilter.getCategoryGroupLike());
        }
        if (serviceCategoryFilter.getUserId() != null) {
            query += " and :id like user_id ";
            params.put("id", serviceCategoryFilter.getUserId());
        }
        TypedQuery<Category> typedQuery = em.createQuery(query, Category.class);
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            typedQuery.setParameter(entry.getKey(), entry.getValue());
        }
        return typedQuery.getResultList();
    }
}