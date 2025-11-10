package ru.khmelevskoy.repository;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ServiceCategoryFilter {
    private String categoryGroupLike;
    private Long userId;
}