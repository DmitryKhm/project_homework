package ru.khmelevskoy.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.khmelevskoy.dto.TransactionDTO;
import ru.khmelevskoy.dto.UserDTO;
import ru.khmelevskoy.securitry.CustomUserDetails;
import ru.khmelevskoy.service.ServiceTransaction;
import ru.khmelevskoy.service.ServiceUser;
import ru.khmelevskoy.web.form.ReportForm;
import ru.khmelevskoy.web.form.TransactionsForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class TransactionController {

    private final ServiceTransaction serviceTransaction;

    private final ServiceUser serviceUser;

    @GetMapping("/transactions")
    public String getAllTransactions(Model model) {
        model.addAttribute("form", new TransactionsForm());
        return "transactions";
    }

    @PostMapping("/transactions")
    public String postAllTransactions(@ModelAttribute("form") @Valid TransactionsForm form, BindingResult result,
                                      Model model, HttpServletRequest request
    ) {
        HttpSession session = request.getSession();
        if (!result.hasErrors()) {
            Long userId = currentUser().getId();
            List<TransactionDTO> transactions = serviceTransaction.getTransactions(userId, form.getAccountId());
            if (!transactions.isEmpty()) {
                session.setAttribute("accountId", form.getAccountId());
                return "redirect:/show-transactions";
            }
        }
        result.addError(new FieldError("form", "accountId", "Incorrect Id"));
        model.addAttribute("form", form);
        return "transactions";
    }

    @GetMapping("/show-transactions")
    public String getTransactions(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Long userId = currentUser().getId();
        Long accountId = (Long) session.getAttribute("accountId");
        List<TransactionDTO> transactions = serviceTransaction.getTransactions(userId, accountId);
        model.addAttribute("transactions", transactions);
        return "show-transactions";
    }

    @GetMapping("/report")
    public String getReport(Model model) {
        model.addAttribute("form", new ReportForm());
        return "report";
    }

    @PostMapping("/report")
    public String postReport(@ModelAttribute("form") @Valid ReportForm form, BindingResult result, Model model, HttpServletRequest request
    ) {
        if (!result.hasErrors()) {
            HttpSession session = request.getSession();
            Long userId =  currentUser().getId();
            List<TransactionDTO> transactions = serviceTransaction.findReport(userId, form.getCategoryId(), form.getDate());
            if (!transactions.isEmpty()) {
                session.setAttribute("date", form.getDate());
                session.setAttribute("categoryId", form.getCategoryId());
                return "redirect:/show-report";
            }
        }
        result.addError(new FieldError("form", "categoryId", "Incorrect Id"));
        model.addAttribute("form", form);
        return "report";
    }

    @GetMapping("/show-report")
    public String getShowReport(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Long categoryId = (Long) session.getAttribute("categoryId");
        Long userId = currentUser().getId();
        Date date = (Date) session.getAttribute("date");
        List<TransactionDTO> transactions = serviceTransaction.findReport(userId, categoryId, date);
        model.addAttribute("transactions", transactions);
        return "show-report";
    }

    private UserDTO currentUser() {
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        return serviceUser.getUserById(user.getId());
    }
}