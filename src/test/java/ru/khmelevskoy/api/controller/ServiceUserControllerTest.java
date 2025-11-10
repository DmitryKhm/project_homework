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
import ru.khmelevskoy.api.converter.ServiceUserToResponseConverter;
import ru.khmelevskoy.config.SecurityConfiguration;
import ru.khmelevskoy.dto.UserDTO;
import ru.khmelevskoy.securitry.UserRole;
import ru.khmelevskoy.service.ServiceUser;
import ru.khmelevskoy.web.MockSecurityConfiguration;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ServiceUserController.class)
@Import({SecurityConfiguration.class, MockSecurityConfiguration.class})
@RunWith(SpringRunner.class)
class ServiceUserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserDetailsService userDetailsService;

    @MockBean
    ServiceUser serviceUser;

    @SpyBean
    ServiceUserToResponseConverter converter;

    @BeforeEach
    void setUp() {

        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setEmail("mikhail@yandex.ru");
        userDTO.setPassword("some_password");
        userDTO.setRoles(Collections.singleton(UserRole.USER));

        when(serviceUser.getUserById(1L)).thenReturn(userDTO);
        when(serviceUser.findUserByEmailAndPassword("mikhail@yandex.ru","some_password")).thenReturn(userDTO);
    }

    @Test
    @WithUserDetails(value = "mikhail@yandex.ru", userDetailsServiceBeanName = "userDetailsService")
    void login() throws Exception {
        mockMvc.perform(post("/api/login").contentType(MediaType.APPLICATION_JSON).content("{\n" +
                "  \"email\":\"mikhail@yandex.ru\",\n" +
                "  \"password\":\"some_password\"\n" +
                "}").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails(value = "mikhail@yandex.ru", userDetailsServiceBeanName = "userDetailsService")
    void getUserInfo() throws Exception {
        mockMvc.perform(get("/api/get-user-info"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "  \"id\": 1,\n" +
                        "   \"email\": \"mikhail@yandex.ru\"\n" +
                        "}"));
    }

    @Test
    @WithUserDetails(value = "mikhail@yandex.ru", userDetailsServiceBeanName = "userDetailsService")
    void registration() throws Exception {
        mockMvc.perform(post("/api/login").contentType(MediaType.APPLICATION_JSON).content("{\n" +
                        "  \"email\":\"mikhail@yandex.ru\",\n" +
                        "  \"name\": \"Mikhail\",\n" +
                        "  \"password\":\"some_password\"\n" +
                        "}").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}