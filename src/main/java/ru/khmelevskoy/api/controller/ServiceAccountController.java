package ru.khmelevskoy.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.khmelevskoy.api.converter.ServiceAccountToResponseConverter;
import ru.khmelevskoy.api.json.AccountRequest;
import ru.khmelevskoy.api.json.AccountRequestIdAndUserId;
import ru.khmelevskoy.api.json.AccountResponse;
import ru.khmelevskoy.dto.AccountDTO;
import ru.khmelevskoy.dto.UserDTO;
import ru.khmelevskoy.securitry.CustomUserDetails;
import ru.khmelevskoy.service.ServiceAccount;
import ru.khmelevskoy.service.ServiceUser;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class ServiceAccountController extends Controller {

    private final ServiceAccount serviceAccount;

    private final ServiceUser serviceUser;

    private final ServiceAccountToResponseConverter converter;

    @PostMapping("/find-account")
    public ResponseEntity<AccountResponse> findAccountByAccountIdAndUserId(@RequestBody @Valid AccountRequestIdAndUserId request) {
        AccountDTO account = serviceAccount.searchAccountByIdAndUserId(request.getAccountId(), request.getUserId());
        if (account == null) {
            return status(HttpStatus.NOT_FOUND).build();
        } else {
            return ok(converter.convert(account));

        }
    }

    @GetMapping("/find-accounts")
    public ResponseEntity<List<AccountResponse>> findAccountAccounts() {
        long userId = currentUser().getId();

        List<AccountDTO> accounts = serviceAccount.searchAccountsUserId(userId);
        return ok(accounts.stream()
                .map(converter::convert)
                .collect(Collectors.toList()));
    }

    @PostMapping("/delete-accounts")
    public ResponseEntity<AccountResponse> deleteAccount(@RequestBody @Valid AccountRequestIdAndUserId request) {

        boolean type = serviceAccount.deleteAccountByIdAndUserId(request.getAccountId(), request.getUserId());

        if (type) {
            return status(HttpStatus.ACCEPTED).build();
        } else {
            return status(HttpStatus.BAD_REQUEST).build();
        }

    }

    @PostMapping("/insert-account")
    public ResponseEntity<AccountResponse> insertAccount(@RequestBody @Valid AccountRequest request) {

        serviceAccount.createAccount(request.getAccountName(), request.getAccountValue(), request.getUserId());

        return status(HttpStatus.ACCEPTED).build();
    }

    private UserDTO currentUser() {
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        return serviceUser.getUserById(user.getId());
    }
}