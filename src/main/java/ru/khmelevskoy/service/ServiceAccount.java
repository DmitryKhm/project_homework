package ru.khmelevskoy.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.khmelevskoy.converter.AccountToAccountDtoConverter;
import ru.khmelevskoy.dto.AccountDTO;
import ru.khmelevskoy.entity.Account;
import ru.khmelevskoy.entity.User;
import ru.khmelevskoy.repository.ServiceAccountRepository;
import ru.khmelevskoy.repository.ServiceUserRepository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ServiceAccount {

    private final ServiceAccountRepository accountRepository;

    private final ServiceUserRepository userRepository;

    private final AccountToAccountDtoConverter converter;

    public AccountDTO searchAccountByIdAndUserId(long accountId, long userId) {
        return converter.convert(accountRepository.getAccountByIdAndUserId(accountId, userId));
    }

    public List<AccountDTO> searchAccountsUserId(long userId) {
        return accountRepository.getAllByUserId(userId).stream()
                .map(converter::convert)
                .collect(Collectors.toList());
    }

    public boolean deleteAccountByIdAndUserId(long accountId, long userId) {
        accountRepository.deleteAccountByIdAndUserId(accountId, userId);
        return (accountRepository.getAccountByIdAndUserId(accountId, userId) == null);
    }

    public AccountDTO createAccount(String accountName, BigDecimal accountValue, Long userId) {
        User user = userRepository.findServiceUserByIdEquals(userId);
        Account account = new Account();
        account.setUser(user);
        account.setAccName(accountName);
        account.setAccValue(accountValue);
        long accountId = accountRepository.save(account).getId();
        if (searchAccountByIdAndUserId(accountId, userId) != null) {
            return converter.convert(account);
        }
        return null;
    }
}