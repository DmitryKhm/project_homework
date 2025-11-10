package ru.khmelevskoy.api.json;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CategoryRequest {

    @NotEmpty
    private String categoryName;

    @NotEmpty
    private String categoryGroup;
}