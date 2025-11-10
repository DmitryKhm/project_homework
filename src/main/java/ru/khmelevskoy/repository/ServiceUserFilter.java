package ru.khmelevskoy.repository;

import lombok.Data;
import lombok.experimental.Accessors;
import ru.khmelevskoy.securitry.UserRole;

@Data
@Accessors(chain = true)
public class ServiceUserFilter {
    private String emailLike;
    private UserRole userRole;
}