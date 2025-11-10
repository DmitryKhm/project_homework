package ru.khmelevskoy.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.khmelevskoy.securitry.CustomGrantedAuthority;
import ru.khmelevskoy.securitry.CustomUserDetails;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ru.khmelevskoy.securitry.UserRole.USER;

@Configuration
public class MockSecurityConfiguration {

    @Bean
    public UserDetailsService userDetailsService() {

        return username -> new CustomUserDetails(
                1L,
                "mikhail@yandex.ru",
                "password",
                Stream.of(USER)
                        .map(CustomGrantedAuthority::new)
                        .collect(Collectors.toList()));

    }
}