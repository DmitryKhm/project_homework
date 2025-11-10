package ru.khmelevskoy.web.form;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class RegistrationForm {
    @Email
    @NotEmpty
    String email;

    @NotEmpty
    String name;

    @NotEmpty
    String password;
}