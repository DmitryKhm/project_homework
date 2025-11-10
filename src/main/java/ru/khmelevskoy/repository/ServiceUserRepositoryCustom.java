package ru.khmelevskoy.repository;

import ru.khmelevskoy.entity.User;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface ServiceUserRepositoryCustom {
    List<User> findByFilter(ServiceUserFilter userFilter);

}