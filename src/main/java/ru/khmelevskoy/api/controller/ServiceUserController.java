package ru.khmelevskoy.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.khmelevskoy.api.converter.ServiceUserToResponseConverter;
import ru.khmelevskoy.api.json.AuthRequest;
import ru.khmelevskoy.api.json.AuthResponse;
import ru.khmelevskoy.api.json.RegistrationRequest;
import ru.khmelevskoy.dto.UserDTO;
import ru.khmelevskoy.securitry.CustomUserDetails;
import ru.khmelevskoy.service.ServiceUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RequiredArgsConstructor
@RestController
@RequestMapping("api")
public class ServiceUserController {

    private final ServiceUserToResponseConverter converter;

    private final ServiceUser serviceUser;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthRequest request,
                                              HttpServletRequest httpServletRequest) {
        UserDTO user = serviceUser.findUserByEmailAndPassword(request.getEmail(),
                (request.getPassword()));
        if (user == null) {
            return status(HttpStatus.UNAUTHORIZED).build();
        }
        HttpSession session = httpServletRequest.getSession();
        session.setAttribute("userId", user.getId());
        return ok(converter.convert(user));
    }

    @GetMapping("/get-user-info")
    public ResponseEntity<AuthResponse> getUserInfo() {

        return ok(converter.convert(currentUser()));
    }

    @PostMapping("/registration")
    public ResponseEntity<AuthResponse> registration(@RequestBody @Valid RegistrationRequest request,
                                                     HttpServletRequest httpServletRequest) {
        UserDTO user = serviceUser.createUser(request.getEmail(), request.getName(), request.getPassword());
        if (user == null) {
            return status(HttpStatus.NOT_FOUND).build();
        }
        HttpSession session = httpServletRequest.getSession();
        session.setAttribute("userId", user.getId());
        return ok(converter.convert(user));
    }

    private UserDTO currentUser() {
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        return serviceUser.getUserById(user.getId());
    }

}