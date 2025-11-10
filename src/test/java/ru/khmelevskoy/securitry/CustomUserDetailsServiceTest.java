package ru.khmelevskoy.securitry;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import ru.khmelevskoy.dto.UserDTO;
import ru.khmelevskoy.service.ServiceUser;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
class CustomUserDetailsServiceTest {

    @Autowired
    CustomUserDetailsService subj;

    @MockBean
    ServiceUser serviceUser;

    @Test
    public void loadUserByUsername() {

        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setEmail("mikhail@yandex.ru");
        userDTO.setPassword("some_password");
        userDTO.setRoles(Collections.singleton(UserRole.USER));
        when(serviceUser.getUserByEmail("mikhail@yandex.ru")).thenReturn(userDTO);

        UserDetails userDetails = subj.loadUserByUsername("mikhail@yandex.ru");

        assertNotNull(userDetails);
        assertEquals("mikhail@yandex.ru", userDetails.getUsername());
        assertEquals("some_password", userDetails.getPassword());
        assertEquals(1, userDetails.getAuthorities().size());
    }
}