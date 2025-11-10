package ru.khmelevskoy.api.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.khmelevskoy.api.json.AuthResponse;
import ru.khmelevskoy.dto.UserDTO;

@Component
public class ServiceUserToResponseConverter implements Converter<UserDTO, AuthResponse> {
    @Override
    public AuthResponse convert(UserDTO user) {
        return new AuthResponse(user.getId(), user.getEmail());
    }
}