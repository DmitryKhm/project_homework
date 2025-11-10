package ru.khmelevskoy.api.json;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategoryResponse {
    private String categoryName;
    private String categoryGroup;
}