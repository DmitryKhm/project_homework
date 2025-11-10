package ru.khmelevskoy.web.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.khmelevskoy.config.SecurityConfiguration;
import ru.khmelevskoy.dto.TransactionDTO;
import ru.khmelevskoy.dto.UserDTO;
import ru.khmelevskoy.securitry.UserRole;
import ru.khmelevskoy.service.ServiceTransaction;
import ru.khmelevskoy.service.ServiceUser;
import ru.khmelevskoy.web.MockSecurityConfiguration;
import ru.khmelevskoy.web.form.ReportForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TransactionController.class)
@Import({SecurityConfiguration.class, MockSecurityConfiguration.class})
@RunWith(SpringRunner.class)
class TransactionControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    ServiceTransaction serviceTransaction;

    @MockBean
    ServiceUser serviceUser;

    @Autowired
    UserDetailsService userDetailsService;

    @BeforeEach
    void setUp() throws ParseException {
        TransactionDTO transaction = new TransactionDTO();
        transaction.setId(1L);
        transaction.setValue(100L);
        transaction.setAccountTo(1L);
        transaction.setAccountFrom(2L);
        String dateString = "7-Jun-2013";
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        Date date = formatter.parse(dateString);
        transaction.setDate(date);
        List<TransactionDTO> transactionDTOList = new ArrayList<>();
        transactionDTOList.add(transaction);
        when(serviceTransaction.getTransactions(1L, 1L)).thenReturn(transactionDTOList);
        when(serviceTransaction.findReport(1L, 1L, date)).thenReturn(transactionDTOList);

        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setEmail("mikhail@yandex.ru");
        userDTO.setPassword("some_password");
        userDTO.setRoles(Collections.singleton(UserRole.USER));

        when(serviceUser.getUserById(1L)).thenReturn(userDTO);
    }

    @Test
    @WithUserDetails(value = "mikhail@yandex.ru", userDetailsServiceBeanName = "userDetailsService")
    void getAllTransactions() throws Exception {
        mockMvc.perform(get("/transactions"))
                .andExpect(status().isOk())
                .andExpect(view().name("transactions"));
    }

    @Test
    @WithUserDetails(value = "mikhail@yandex.ru", userDetailsServiceBeanName = "userDetailsService")
    void postAllTransactionsWhenEmpty() throws Exception {
        mockMvc.perform(get("/transactions"))
                .andExpect(status().isOk())
                .andExpect(view().name("transactions"));
    }

    @Test
    @WithUserDetails(value = "mikhail@yandex.ru", userDetailsServiceBeanName = "userDetailsService")
    void getTransactions() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession mockHttpSession = mock(HttpSession.class);
        mockHttpSession.setAttribute("userId", 1L);
        mockHttpSession.setAttribute("accountId", 1L);

        when(request.getSession()).thenReturn(mockHttpSession);
        when(request.getSession()).thenReturn(mockHttpSession);
        when(mockHttpSession.getAttribute("userId")).thenReturn(1L);
        when(mockHttpSession.getAttribute("accountId")).thenReturn(1L);

        mockMvc.perform(get("/show-transactions"))
                .andExpect(status().isOk())
                .andExpect(view().name("show-transactions"));
    }

    @Test
    @WithUserDetails(value = "mikhail@yandex.ru", userDetailsServiceBeanName = "userDetailsService")
    void getReport() throws Exception {
        mockMvc.perform(get("/report"))
                .andExpect(status().isOk())
                .andExpect(view().name("report"));
    }

    @Test
    @WithUserDetails(value = "mikhail@yandex.ru", userDetailsServiceBeanName = "userDetailsService")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    void postReport() throws Exception {

        ReportForm reportForm = new ReportForm();
        String dateString = "2022-07-07";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date date = formatter.parse(dateString);
        reportForm.setCategoryId(1L);
        reportForm.setDate(date);
        mockMvc.perform(post("/report"))
                .andExpect(view().name("report"));
    }

    @Test
    @WithUserDetails(value = "mikhail@yandex.ru", userDetailsServiceBeanName = "userDetailsService")
    void getShowReport() throws Exception {
        mockMvc.perform(get("/show-report"))
                .andExpect(status().isOk())
                .andExpect(view().name("show-report"));
    }
}