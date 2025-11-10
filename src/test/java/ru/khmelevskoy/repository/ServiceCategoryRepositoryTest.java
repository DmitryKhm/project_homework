package ru.khmelevskoy.repository;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import ru.khmelevskoy.entity.Category;

import javax.persistence.EntityManager;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:application.yml")
class ServiceCategoryRepositoryTest {

    @Autowired
    private ServiceCategoryRepository subj;

    @Autowired
    private EntityManager entityManager;

    @Test
    void getById() {
        Category category = subj.getById(1L);
        assertNotNull(category);
    }

    @Test
    public void findByFilter() {
        ServiceCategoryFilter filter = new ServiceCategoryFilter().setCategoryGroupLike("еда").setUserId(1L);
        List<Category> categories = subj.findByFilter(filter);
        assertNotNull(categories);
        assertEquals(1, categories.size());
    }
}