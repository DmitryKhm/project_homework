package ru.khmelevskoy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.khmelevskoy.entity.Category;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface ServiceCategoryRepository extends JpaRepository<Category, Long>,ServiceCategoryRepositoryCustom {

    Category getById(Long CategoryId);

    Category getByIdAndAndUserId(Long categoryId, Long userId);

    List<Category> getAllByUserId(Long userId);
}