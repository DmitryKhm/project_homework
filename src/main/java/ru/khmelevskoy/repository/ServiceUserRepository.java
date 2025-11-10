package ru.khmelevskoy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.khmelevskoy.entity.User;

import javax.transaction.Transactional;

@Transactional
public interface ServiceUserRepository extends JpaRepository<User, Long>,ServiceUserRepositoryCustom {

    User findByEmailAndPassword(String email, String password);

    User findServiceUserByIdEquals(Long Id);

    User findByEmail(String email);

}