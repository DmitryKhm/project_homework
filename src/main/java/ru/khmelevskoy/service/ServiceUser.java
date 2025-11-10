package ru.khmelevskoy.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.khmelevskoy.converter.UserToUserDtoConverter;
import ru.khmelevskoy.dto.UserDTO;
import ru.khmelevskoy.entity.User;
import ru.khmelevskoy.repository.ServiceUserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ServiceUser {

    private final ServiceUserRepository serviceUserRepository;

    private final UserToUserDtoConverter converter;

    private final PasswordEncoder passwordEncoder;

    public UserDTO findUserByEmailAndPassword(String email, String password) {
        String hash = passwordEncoder.encode(password);
        if (getUserDTO(email, hash) == null) {
            return null;
        } else {
            return converter.convert(serviceUserRepository.findByEmailAndPassword(email, hash));
        }
    }

    public UserDTO getUserById(Long userId) {
        return converter.convert(serviceUserRepository.getById(userId));
    }

    public UserDTO getUserByEmail(String email) {
        return converter.convert(serviceUserRepository.findByEmail(email));
    }


    public UserDTO createUser(String email, String name, String password) {
        String hash = passwordEncoder.encode(password);
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setPassword(hash);
        serviceUserRepository.save(user);
        if (getUserDTO(email, hash) == null) {
            return null;
        }
        return converter.convert(user);
    }

    private UserDTO getUserDTO(String email, String hash) {
        return Optional.ofNullable(serviceUserRepository.findByEmailAndPassword(email, hash))
                .map(converter::convert)
                .orElse(null);
    }
}