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
import ru.khmelevskoy.dto.AccountDTO;
import ru.khmelevskoy.dto.UserDTO;
import ru.khmelevskoy.securitry.UserRole;
import ru.khmelevskoy.service.ServiceAccount;
import ru.khmelevskoy.service.ServiceUser;
import ru.khmelevskoy.web.MockSecurityConfiguration;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ServiceAccountController.class)
@Import({SecurityConfiguration.class, MockSecurityConfiguration.class})
@RunWith(SpringRunner.class)
class ServiceAccountControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ServiceAccount serviceAccount;

    @SpyBean
    ServiceAccountToResponseConverter converter;

    @Autowired
    UserDetailsService userDetailsService;

    @MockBean
    ServiceUser serviceUser;

    @BeforeEach
    void setUp() {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setEmail("mikhail@yandex.ru");
        userDTO.setPassword("some_password");
        userDTO.setRoles(Collections.singleton(UserRole.USER));

        List<AccountDTO> accounts = new ArrayList<>();
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setAccName("TestAcc");
        accountDTO.setUserId(1L);
        accountDTO.setAccValue(BigDecimal.valueOf(100L));
        accountDTO.setId(1L);
        accounts.add(accountDTO);

        when(serviceUser.getUserById(1L)).thenReturn(userDTO);
        when(serviceAccount.searchAccountByIdAndUserId(1L, 1L)).thenReturn(accountDTO);
        when(serviceAccount.searchAccountsUserId(1L)).thenReturn(accounts);
        when(serviceAccount.deleteAccountByIdAndUserId(1L,1L)).thenReturn(true);
    }

    @Test
    @WithUserDetails(value = "mikhail@yandex.ru", userDetailsServiceBeanName = "userDetailsService")
    void findAccountByAccountIdAndUserId() throws Exception {
        mockMvc.perform(post("/api/find-account").contentType(MediaType.APPLICATION_JSON).content("{\n" +
                        "  \"userId\":\"1\",\n" +
                        "  \"accountId\":\"1\"\n" +
                        "}").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

   @Test
   @WithUserDetails(value = "mikhail@yandex.ru", userDetailsServiceBeanName = "userDetailsService")
    void findAccountAccounts() throws Exception {
        mockMvc.perform(get("/api/find-accounts"))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails(value = "mikhail@yandex.ru", userDetailsServiceBeanName = "userDetailsService")
    void deleteAccount() throws Exception {
        mockMvc.perform(post("/api/delete-accounts").contentType(MediaType.APPLICATION_JSON).content("{\n" +
                        "  \"userId\":\"1\",\n" +
                        "  \"accountId\": \"1\"\n" +
                        "}").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }

    @Test
    @WithUserDetails(value = "mikhail@yandex.ru", userDetailsServiceBeanName = "userDetailsService")
    void insertAccount() throws Exception {
        mockMvc.perform(post("/api/insert-account").contentType(MediaType.APPLICATION_JSON).content("{\n" +
                        "  \"userId\":\"1\",\n" +
                        "  \"accountName\": \"TestAcc\",\n" +
                        "  \"accountValue\": \"100\"\n" +
                        "}").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }
}