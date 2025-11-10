package ru.khmelevskoy.api.json;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class AuthRequest {

    @Email
    @NotNull
    String email;


    @NotNull
    String password;
}