package ru.khmelevskoy.repository;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import ru.khmelevskoy.entity.Transaction;

import javax.persistence.EntityManager;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:application.yml")
class ServiceTransactionRepositoryTest {

    @Autowired
    private ServiceTransactionRepository subj;

    @Autowired
    private EntityManager entityManager;

    @Test
    void getAllByAccountFromOrAccountTo() {
        Transaction transaction = subj.getById(1L);
        assertNotNull(transaction);
    }

    @Test
    public void findByFilter() {
        ServiceTransactionFilter filter = new ServiceTransactionFilter().setTransactionId(1L).setTransactionValue(100L);
        List<Transaction> transactions = subj.findByFilter(filter);
        assertNotNull(transactions);
        assertEquals(1, transactions.size());
    }
}