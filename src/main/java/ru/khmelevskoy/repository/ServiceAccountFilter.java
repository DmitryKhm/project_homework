package ru.khmelevskoy.repository;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ServiceAccountFilter {
    private String accountNameLike;
    private Long userId;
}