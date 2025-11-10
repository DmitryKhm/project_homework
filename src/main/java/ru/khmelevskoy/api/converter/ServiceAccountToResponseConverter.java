package ru.khmelevskoy.api.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.khmelevskoy.api.json.AccountResponse;
import ru.khmelevskoy.dto.AccountDTO;

@Component
public class ServiceAccountToResponseConverter implements Converter<AccountDTO, AccountResponse> {

    @Override
    public AccountResponse convert(AccountDTO account) {
        return new AccountResponse(account.getId(), account.getUserId(),
                account.getAccValue(), account.getAccName());
    }
}