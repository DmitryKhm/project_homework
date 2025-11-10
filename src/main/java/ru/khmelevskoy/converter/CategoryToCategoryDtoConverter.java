package ru.khmelevskoy.converter;

import org.springframework.stereotype.Service;
import ru.khmelevskoy.dto.CategoryDTO;
import ru.khmelevskoy.entity.Category;

@Service
public class CategoryToCategoryDtoConverter implements Converter<Category, CategoryDTO> {

    @Override
    public CategoryDTO convert(Category source) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryName(source.getCategoryName());
        categoryDTO.setCategoryGroup(source.getCategoryGroup());
        categoryDTO.setId(source.getId());
        categoryDTO.setUserId(source.getUser().getId());
        return categoryDTO;
    }
}