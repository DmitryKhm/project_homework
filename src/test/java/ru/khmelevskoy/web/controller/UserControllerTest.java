package ru.khmelevskoy.web.controller;


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
import ru.khmelevskoy.dto.UserDTO;
import ru.khmelevskoy.securitry.UserRole;
import ru.khmelevskoy.service.ServiceUser;
import ru.khmelevskoy.web.MockSecurityConfiguration;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@Import({SecurityConfiguration.class, MockSecurityConfiguration.class})
@RunWith(SpringRunner.class)
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserDetailsService userDetailsService;

    @MockBean
    ServiceUser serviceUser;

    @Test
    void getLogin() throws Exception {
        mockMvc.perform(get("/login-form"))
                .andExpect(status().isOk())
                .andExpect(view().name("login-form"));
    }

    @WithUserDetails(value = "mikhail@yandex.ru", userDetailsServiceBeanName = "userDetailsService")
    @Test
    void index() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setEmail("mikhail@yandex.ru");
        userDTO.setPassword("password");
        userDTO.setRoles(Collections.singleton(UserRole.USER));
        when(serviceUser.getUserById(1L)).thenReturn(userDTO);
        when(serviceUser.getUserByEmail("mikhail@yandex.ru")).thenReturn(userDTO);

        mockMvc.perform(get("/personal-area"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("id", 1L))
                .andExpect(model().attribute("email", "mikhail@yandex.ru"))
                .andExpect(view().name("personal-area"));
    }

    @Test
    void registration() throws Exception {
        mockMvc.perform(get("/registration"))
                .andExpect(status().isOk())
                .andExpect(view().name("registration"));
    }
}