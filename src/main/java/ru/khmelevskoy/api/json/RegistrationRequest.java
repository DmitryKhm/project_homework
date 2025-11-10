package ru.khmelevskoy.api.json;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class RegistrationRequest {

    @Email
    @NotNull
    String email;

    @NotNull
    String name;

    @NotNull
    String password;
}