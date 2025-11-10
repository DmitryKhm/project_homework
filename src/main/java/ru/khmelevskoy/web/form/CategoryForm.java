package ru.khmelevskoy.web.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CategoryForm {

    @NotEmpty
    String categoryName;

    @NotEmpty
    String categoryGroup;
}