package ru.khmelevskoy.web.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AccountController.class)
@Import({SecurityConfiguration.class, MockSecurityConfiguration.class})
@RunWith(SpringRunner.class)
class AccountControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserDetailsService userDetailsService;

    @MockBean
    ServiceUser serviceUser;

    @MockBean
    ServiceAccount serviceAccount;

    @InjectMocks
    MockHttpSession mockHttpSession;

    @BeforeEach
    void setUp() {
        AccountDTO accountDTO = new AccountDTO();
        List<AccountDTO> accounts = new ArrayList<>();
        accountDTO.setId(1L);
        accountDTO.setAccValue(BigDecimal.valueOf(100L));
        accountDTO.setUserId(1L);
        accountDTO.setAccName("TestAcc");
        accounts.add(accountDTO);
        when(serviceAccount.searchAccountsUserId(1L)).thenReturn(accounts);
        when(serviceAccount.searchAccountByIdAndUserId(1L, 1L)).thenReturn(accountDTO);

        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("mikhail@yandex.ru");
        userDTO.setPassword("password");
        userDTO.setId(1L);
        userDTO.setRoles(Collections.singleton(UserRole.USER));
        when(serviceUser.getUserById(1L)).thenReturn(userDTO);
    }

    @Test
    @WithUserDetails(value = "mikhail@yandex.ru", userDetailsServiceBeanName = "userDetailsService")
    void getInsertAccount() throws Exception {

        mockMvc.perform(get("/insert-account")).andExpect(status().isOk()).andExpect(view().name("insert-account"));
    }

    @Test
    @WithUserDetails(value = "mikhail@yandex.ru", userDetailsServiceBeanName = "userDetailsService")
    void postInsertAccount() throws Exception {
        mockMvc.perform(post("/insert-account")).andExpect(status().isOk()).andExpect(view().name("insert-account"));
    }

    @Test
    @WithUserDetails(value = "mikhail@yandex.ru", userDetailsServiceBeanName = "userDetailsService")
    void getAccounts() throws Exception {
        AccountDTO accountDTO = new AccountDTO();
        List<AccountDTO> accounts = new ArrayList<>();
        accountDTO.setId(1L);
        accountDTO.setAccValue(BigDecimal.valueOf(100L));
        accountDTO.setUserId(1L);
        accountDTO.setAccName("TestAcc");
        accounts.add(accountDTO);

        mockMvc.perform(get("/accounts")).andExpect(status().isOk())
                .andExpect(model().attribute("accounts", accounts))
                .andExpect(view().name("accounts"));
    }

    @Test
    @WithUserDetails(value = "mikhail@yandex.ru", userDetailsServiceBeanName = "userDetailsService")
    void getAccountByIdAndUserId() throws Exception {

        mockHttpSession.setAttribute("accountId", 1L);

        mockMvc.perform(get("/show-account-by-id").session(mockHttpSession)).andExpect(status().isOk())
                .andExpect(model().attribute("accountId", 1L))
                .andExpect(model().attribute("accountName", "TestAcc"))
                .andExpect(model().attribute("accountValue", BigDecimal.valueOf(100L)))
                .andExpect(view().name("show-account-by-id"));
    }

    @Test
    @WithUserDetails(value = "mikhail@yandex.ru", userDetailsServiceBeanName = "userDetailsService")
    void getAccountById() throws Exception {
        mockMvc.perform(get("/account-id")).andExpect(status().isOk()).andExpect(view().name("account-id"));
    }

    @Test
    @WithUserDetails(value = "mikhail@yandex.ru", userDetailsServiceBeanName = "userDetailsService")
    void postAccountById() throws Exception {
        mockHttpSession.setAttribute("accountId", 1L);
        mockMvc.perform(post("/account-id").param("accountId", String.valueOf(1L)))
                .andExpect(view().name("redirect:/show_account_by_id"));
    }

    @Test
    @WithUserDetails(value = "mikhail@yandex.ru", userDetailsServiceBeanName = "userDetailsService")
    void postDeleteAccountForm() throws Exception {
        mockMvc.perform(post("/delete-account").param("accountId", String.valueOf(1L)))
                .andExpect(view().name("redirect:/accounts"));
    }

    @Test
    @WithUserDetails(value = "mikhail@yandex.ru", userDetailsServiceBeanName = "userDetailsService")
    void getDeleteAccountForm() throws Exception {
        mockMvc.perform(get("/delete-account").param("accountId", String.valueOf(1L)))
                .andExpect(view().name("delete-account"));
    }
}