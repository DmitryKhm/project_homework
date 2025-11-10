package ru.khmelevskoy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static ru.khmelevskoy.securitry.UserRole.ADMIN;
import static ru.khmelevskoy.securitry.UserRole.USER;

@Configuration

public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/login-form").permitAll()
                .antMatchers("/personal-area").hasAnyRole(USER.name(), ADMIN.name())
                .antMatchers("/registration").permitAll()
                .antMatchers("/transactions").hasAnyRole(USER.name(), ADMIN.name())
                .antMatchers("/report").hasAnyRole(USER.name(), ADMIN.name())
                .antMatchers("/insert-account").hasAnyRole(USER.name(),ADMIN.name())
                .antMatchers("/accounts").hasAnyRole(USER.name(), ADMIN.name())
                .antMatchers("/account-id").hasAnyRole(USER.name(), ADMIN.name())
                .antMatchers("/delete-account").hasAnyRole(USER.name(), ADMIN.name())
                .antMatchers("/insert-category").hasAnyRole(USER.name(), ADMIN.name())
                .antMatchers("/show-category").hasAnyRole(USER.name(), ADMIN.name())
                .antMatchers("/api/find-account").hasAnyRole(USER.name(), ADMIN.name())
                .and()
                .formLogin()
                .usernameParameter("email")
                .passwordParameter("password")
                .loginPage("/login-form")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/personal-area")
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login-form");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}