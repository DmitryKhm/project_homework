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
import ru.khmelevskoy.dto.AccountDTO;
import ru.khmelevskoy.dto.UserDTO;
import ru.khmelevskoy.securitry.CustomUserDetails;
import ru.khmelevskoy.service.ServiceAccount;
import ru.khmelevskoy.service.ServiceUser;
import ru.khmelevskoy.web.form.AccountForm;
import ru.khmelevskoy.web.form.DeleteAccountForm;
import ru.khmelevskoy.web.form.FindAccountForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class AccountController {

    private final ServiceAccount serviceAccount;

    private final ServiceUser serviceUser;

    @GetMapping("/insert-account")
    public String getInsertAccount(Model model) {
        model.addAttribute("form", new AccountForm());
        return "insert-account";
    }

    @PostMapping("/insert-account")
    public String postInsertAccount(@ModelAttribute("form") @Valid AccountForm form, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            Long userId = currentUser().getId();
            serviceAccount.createAccount(form.getAccountName(), form.getAccountValue(), userId);
            return "redirect:/accounts";
        }
        result.addError(new FieldError("form", "Account Value", "Fields shouldn't be empty!"));
        model.addAttribute("form", form);
        return "insert-account";
    }

    @GetMapping("/accounts")
    public String getAccounts(Model model) {
        Long userId = currentUser().getId();
        List<AccountDTO> accounts = serviceAccount.searchAccountsUserId(userId);
        if (accounts.isEmpty()) {
            return "redirect:/insert-account";
        }
        model.addAttribute("accounts", accounts);
        return "accounts";
    }

    @GetMapping("/show-account-by-id")
    public String getAccountByIdAndUserId(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Long userId = currentUser().getId();
        Long accountId = (Long) session.getAttribute("accountId");
        AccountDTO account = serviceAccount.searchAccountByIdAndUserId(accountId, userId);
        if (account == null) {
            return "redirect:/show-account-by-id";
        }
        model.addAttribute("accountId", account.getId())
                .addAttribute("accountName", account.getAccName())
                .addAttribute("accountValue", account.getAccValue());
        return "show-account-by-id";
    }

    @GetMapping("/account-id")
    public String getAccountById(Model model) {
        model.addAttribute("form", new FindAccountForm());
        return "account-id";
    }

    @PostMapping("/account-id")
    public String postAccountById(@ModelAttribute("form") @Valid FindAccountForm form, BindingResult result, Model model,
                                  HttpServletRequest request) {
        if (!result.hasErrors()) {
            HttpSession session = request.getSession();
            session.setAttribute("accountId", form.getAccountId());
            return "redirect:/show_account_by_id";
        }
        result.addError(new FieldError("form", "accountId", "Field couldn't be null"));
        model.addAttribute("form", form);
        return "account-id";
    }

    @GetMapping("/delete-account")
    public String getDeleteAccountForm(Model model) {
        model.addAttribute("form", new DeleteAccountForm());
        return "delete-account";
    }

    @PostMapping("/delete-account")
    public String postDeleteAccountForm(@ModelAttribute("form") @Valid DeleteAccountForm form, BindingResult result,
                                        Model model, HttpServletRequest request) {
        if (!result.hasErrors()) {
            Long userId = currentUser().getId();
            serviceAccount.deleteAccountByIdAndUserId(form.getAccountId(),
                    userId);
            model.addAttribute("form", form);
            return "redirect:/accounts";
        }
        return "delete-account";
    }

    private UserDTO currentUser() {
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        return serviceUser.getUserById(user.getId());
    }
}