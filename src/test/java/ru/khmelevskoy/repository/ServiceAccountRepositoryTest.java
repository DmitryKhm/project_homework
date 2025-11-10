package ru.khmelevskoy.repository;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import ru.khmelevskoy.entity.Account;

import javax.persistence.EntityManager;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:application.yml")
class ServiceAccountRepositoryTest {

    @Autowired
    private ServiceAccountRepository subj;

    @Autowired
    private EntityManager entityManager;

    @Test
    public void findByAccountId() {
        Account account = subj.getAccountByIdAndUserId(1L, 1L);
        assertNotNull(account);
    }

    @Test
    public void findByFilter() {
        ServiceAccountFilter filter = new ServiceAccountFilter().setAccountNameLike("Альфа-Банк").setUserId(1L);
        List<Account> accounts = subj.findByFilter(filter);
        assertNotNull(accounts);
        assertEquals(1, accounts.size());
    }
}