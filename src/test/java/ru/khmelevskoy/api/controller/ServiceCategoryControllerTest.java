package ru.khmelevskoy.api.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.khmelevskoy.api.converter.ServiceAccountToResponseConverter;
import ru.khmelevskoy.config.SecurityConfiguration;
import ru.khmelevskoy.dto.CategoryDTO;
import ru.khmelevskoy.dto.UserDTO;
import ru.khmelevskoy.securitry.UserRole;
import ru.khmelevskoy.service.ServiceCategory;
import ru.khmelevskoy.service.ServiceUser;
import ru.khmelevskoy.web.MockSecurityConfiguration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ServiceCategoryController.class)
@Import({SecurityConfiguration.class, MockSecurityConfiguration.class})
@RunWith(SpringRunner.class)
class ServiceCategoryControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ServiceCategory serviceCategory;

    @SpyBean
    ServiceAccountToResponseConverter converter;

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
        List<CategoryDTO > categoryDTOS = new ArrayList<>();
        categoryDTOS.add(categoryDTO);

        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setEmail("mikhail@yandex.ru");
        userDTO.setPassword("some_password");
        userDTO.setRoles(Collections.singleton(UserRole.USER));

        when(serviceUser.getUserById(1L)).thenReturn(userDTO);
        when(serviceCategory.findCategories(1L)).thenReturn(categoryDTOS);

    }

    @Test
    @WithUserDetails(value = "mikhail@yandex.ru", userDetailsServiceBeanName = "userDetailsService")
    void insertCategory() throws Exception {
        mockMvc.perform(post("/api/insert-category").contentType(MediaType.APPLICATION_JSON).content("{\n" +
                        "  \"categoryName\":\"Еда\",\n" +
                        "  \"categoryGroup\":\"Пиво\"\n" +
                        "}").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }

    @Test
    @WithUserDetails(value = "mikhail@yandex.ru", userDetailsServiceBeanName = "userDetailsService")
    void getCategories() throws Exception {
        mockMvc.perform(get("/api/find-categories"))
                .andExpect(status().isOk());
    }
}