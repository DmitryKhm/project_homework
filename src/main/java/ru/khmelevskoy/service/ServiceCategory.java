package ru.khmelevskoy.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.khmelevskoy.converter.CategoryToCategoryDtoConverter;
import ru.khmelevskoy.dto.CategoryDTO;
import ru.khmelevskoy.entity.Category;
import ru.khmelevskoy.entity.User;
import ru.khmelevskoy.repository.ServiceCategoryRepository;
import ru.khmelevskoy.repository.ServiceUserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServiceCategory {

    private final ServiceUserRepository userRepository;

    private final ServiceCategoryRepository categoryRepository;

    private final CategoryToCategoryDtoConverter converter;

    public CategoryDTO createCategory(Long userId, String categoryName, String categoryGroup) {
        User user = userRepository.getById(userId);
        Category category = new Category();
        category.setUser(user);
        category.setCategoryName(categoryName);
        category.setCategoryGroup(categoryGroup);
        Long categoryId = categoryRepository.save(category).getId();
        if (categoryRepository.getById(categoryId) != null) {
            return converter.convert(category);
        }
        return null;
    }

    public List<CategoryDTO> findCategories(Long userId) {
        return categoryRepository.getAllByUserId(userId).stream()
                .map(converter::convert)
                .collect(Collectors.toList());
    }
}