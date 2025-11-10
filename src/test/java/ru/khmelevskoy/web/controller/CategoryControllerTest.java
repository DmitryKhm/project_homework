package ru.khmelevskoy.web.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.khmelevskoy.config.SecurityConfiguration;
import ru.khmelevskoy.dto.CategoryDTO;
import ru.khmelevskoy.dto.UserDTO;
import ru.khmelevskoy.securitry.UserRole;
import ru.khmelevskoy.service.ServiceCategory;
import ru.khmelevskoy.service.ServiceUser;
import ru.khmelevskoy.web.MockSecurityConfiguration;
import ru.khmelevskoy.web.form.CategoryForm;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoryController.class)
@Import({SecurityConfiguration.class, MockSecurityConfiguration.class})
@RunWith(SpringRunner.class)
class CategoryControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ServiceCategory serviceCategory;

    @Autowired
    UserDetailsService userDetailsService;

    @MockBean
    ServiceUser serviceUser;

    @BeforeEach
    void setUp() {

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setUserId(1L);
        categoryDTO.setCategoryGroup("Еда");
        categoryDTO.setCategoryName("Пиво");
        List<CategoryDTO> categoryDTOS = new ArrayList<>();
        categoryDTOS.add(categoryDTO);

        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setEmail("mikhail@yandex.ru");
        userDTO.setPassword("some_password");
        userDTO.setRoles(Collections.singleton(UserRole.USER));

        when(serviceUser.getUserById(1L)).thenReturn(userDTO);
        when(serviceCategory.findCategories(1L)).thenReturn(categoryDTOS);

        CategoryForm form = mock(CategoryForm.class);
        form.setCategoryName("Попиц");
        form.setCategoryGroup("Еда");
        when(form.getCategoryName()).thenReturn("Попиц");
        when(form.getCategoryGroup()).thenReturn("Еда");

    }

    @Test
    @WithUserDetails(value = "mikhail@yandex.ru", userDetailsServiceBeanName = "userDetailsService")
    void getCategory() throws Exception {
        mockMvc.perform(get("/insert-category"))
                .andExpect(status().isOk())
                .andExpect(view().name("insert-category"));
    }

    @Test
    @WithUserDetails(value = "mikhail@yandex.ru", userDetailsServiceBeanName = "userDetailsService")
    void postCategory() throws Exception {
        mockMvc.perform(post("/insert-category").param("categoryName", "Сок").param("categoryGroup",
                        "Еда"))
                .andExpect(view().name("redirect:/show-category"));
    }

    @Test
    @WithUserDetails(value = "mikhail@yandex.ru", userDetailsServiceBeanName = "userDetailsService")
    void geAllCategory() throws Exception {
        mockMvc.perform(get("/show-category"))
                .andExpect(status().isOk())
                .andExpect(view().name("show-category"));
    }
}