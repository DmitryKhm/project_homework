package ru.khmelevskoy.converter;

import org.springframework.stereotype.Service;
import ru.khmelevskoy.dto.UserDTO;
import ru.khmelevskoy.entity.User;

@Service
public class UserToUserDtoConverter implements Converter<User, UserDTO> {

    @Override
    public UserDTO convert(User source) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(source.getId());
        userDTO.setPassword(source.getPassword());
        userDTO.setEmail(source.getEmail());
        userDTO.setPassword(source.getPassword());
        userDTO.setRoles(source.getRoles());
        return userDTO;
    }
}