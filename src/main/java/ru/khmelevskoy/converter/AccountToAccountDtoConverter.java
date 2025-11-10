package ru.khmelevskoy.converter;

import org.springframework.stereotype.Service;
import ru.khmelevskoy.dto.AccountDTO;
import ru.khmelevskoy.entity.Account;

@Service
public class AccountToAccountDtoConverter implements Converter<Account, AccountDTO> {

    @Override
    public AccountDTO convert(Account source) {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setAccName(source.getAccName());
        accountDTO.setId(source.getId());
        accountDTO.setUserId(source.getUser().getId());
        accountDTO.setAccValue(source.getAccValue());
        return accountDTO;
    }
}