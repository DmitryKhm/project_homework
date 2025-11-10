package ru.khmelevskoy.repository;

import ru.khmelevskoy.entity.Category;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface ServiceCategoryRepositoryCustom {
    List<Category> findByFilter(ServiceCategoryFilter serviceCategoryFilter);
}