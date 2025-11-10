package ru.khmelevskoy.securitry;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.khmelevskoy.dto.UserDTO;
import ru.khmelevskoy.service.ServiceUser;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final ServiceUser serviceUser;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserDTO user = serviceUser.getUserByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("Can't find user with email " + email);
        }

        return new CustomUserDetails(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                (user.getRoles()
                        .stream()
                        .map(CustomGrantedAuthority::new)
                        .collect(Collectors.toList()))
        );
    }
}