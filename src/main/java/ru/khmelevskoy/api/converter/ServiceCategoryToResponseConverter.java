package ru.khmelevskoy.api.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.khmelevskoy.api.json.CategoryResponse;
import ru.khmelevskoy.dto.CategoryDTO;

@Component
public class ServiceCategoryToResponseConverter implements Converter<CategoryDTO, CategoryResponse> {

    @Override
    public CategoryResponse convert(CategoryDTO category) {
        return new CategoryResponse(category.getCategoryName(), category.getCategoryGroup());
    }
}