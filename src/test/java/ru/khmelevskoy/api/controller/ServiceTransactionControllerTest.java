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
import ru.khmelevskoy.api.converter.ServiceTransactionToResponseConverter;
import ru.khmelevskoy.config.SecurityConfiguration;
import ru.khmelevskoy.dto.TransactionDTO;
import ru.khmelevskoy.dto.UserDTO;
import ru.khmelevskoy.securitry.UserRole;
import ru.khmelevskoy.service.ServiceTransaction;
import ru.khmelevskoy.service.ServiceUser;
import ru.khmelevskoy.web.MockSecurityConfiguration;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ServiceTransactionController.class)
@Import({SecurityConfiguration.class, MockSecurityConfiguration.class})
@RunWith(SpringRunner.class)
class ServiceTransactionControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ServiceTransaction serviceTransaction;

    @SpyBean
    ServiceTransactionToResponseConverter converter;

    @Autowired
    UserDetailsService userDetailsService;

    @MockBean
    ServiceUser serviceUser;

    @BeforeEach
    void setUp() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setAccountFrom(1L);
        transactionDTO.setAccountTo(1L);
        transactionDTO.setId(1L);
        transactionDTO.setValue(100L);
        String dateString = "7-Jun-2013";
        Date date = formatter.parse(dateString);
        transactionDTO.setDate(date);
        List<TransactionDTO> transactions = new ArrayList<>();
        transactions.add(transactionDTO);

        when(serviceTransaction.getTransactions(1L, 1L)).thenReturn(transactions);
        when(serviceTransaction.findReport(1L, 1L, date)).thenReturn(transactions);

        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setEmail("mikhail@yandex.ru");
        userDTO.setPassword("some_password");
        userDTO.setRoles(Collections.singleton(UserRole.USER));

        when(serviceUser.getUserById(1L)).thenReturn(userDTO);
    }

    @Test
    @WithUserDetails(value = "mikhail@yandex.ru", userDetailsServiceBeanName = "userDetailsService")
    void findAllTransaction() throws Exception {
        mockMvc.perform(get("/api/find-transactions").contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"accId\": \"1\"\n" +
                                "}"))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails(value = "mikhail@yandex.ru", userDetailsServiceBeanName = "userDetailsService")
    void getReport() throws Exception {
        mockMvc.perform(get("/api/report").contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"transactionDate\": \"07-June-2013\",\n" +
                                "  \"categoryId\": \"1\"\n" +
                                "}"))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails(value = "mikhail@yandex.ru", userDetailsServiceBeanName = "userDetailsService")
    void createTransaction() throws Exception {
        mockMvc.perform(post("/api/insert-transaction").contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"transactionValue\": \"100\",\n" +
                                "  \"toAccId\": \"1\",\n" +
                                "  \"fromAccId\": \"1\",\n" +
                                "  \"transactionDate\": \"07-June-2013\",\n" +
                                "  \"categoryId\": \"1\"\n" +
                                "}"))
                .andExpect(status().isAccepted());
    }
}