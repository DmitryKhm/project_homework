package ru.khmelevskoy.repository;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import ru.khmelevskoy.entity.User;
import ru.khmelevskoy.securitry.UserRole;

import javax.persistence.EntityManager;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:application.yml")
class ServiceUserRepositoryTest {

    @Autowired
    private ServiceUserRepository subj;

    @Autowired
    private EntityManager entityManager;

    @Test
    public void findByEmail() {
        User user = subj.findByEmail("mikhail@yandex.ru");
        assertNotNull(user);
    }

    @Test
    public void findByFilter() {
        ServiceUserFilter filter = new ServiceUserFilter().setEmailLike("mikhail@yandex.ru")
                .setUserRole(UserRole.USER);
        List<User> users = subj.findByFilter(filter);
        assertNotNull(users);
        assertEquals(1, users.size());
    }
}