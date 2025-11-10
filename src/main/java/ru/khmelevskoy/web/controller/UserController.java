package ru.khmelevskoy.web.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.khmelevskoy.dto.UserDTO;
import ru.khmelevskoy.securitry.CustomUserDetails;
import ru.khmelevskoy.service.ServiceUser;
import ru.khmelevskoy.web.form.RegistrationForm;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@RequiredArgsConstructor
@Controller
public class UserController {

    private final ServiceUser serviceUser;

    @GetMapping("/personal-area")
    public String index(Model model, HttpServletRequest request) {
        UserDTO user = currentUser();
        if (request.getParameterMap().containsKey("error")) {
            model.addAttribute("error", true);
        }
        model.addAttribute("id", user.getId())
                .addAttribute("email", user.getEmail());
        return "personal-area";
    }

    @GetMapping("/login-form")
    public String getLogin(Model model, HttpServletRequest request) {
        if (request.getParameterMap().containsKey("error")) {
            model.addAttribute("error", true);
        }
        return "login-form";
    }

    @GetMapping("/registration")
    public String getRegistration(Model model, HttpServletRequest request) {

        model.addAttribute("form", new RegistrationForm());
        return "registration";
    }

    @PostMapping("/registration")
    public String postRegistration(@ModelAttribute("form") @Valid RegistrationForm form, BindingResult result, Model model
    ) {
        if (!result.hasErrors()) {
            UserDTO user = (serviceUser.findUserByEmailAndPassword(form.getEmail(),
                    (form.getPassword())));
            if (user == null) {
                serviceUser.createUser(form.getEmail(), form.getName(), form.getPassword());
                return "redirect:/login-form";
            }
            result.addError(new FieldError("form", "email", "User already Exists!!!!!"));
        }
        result.addError(new FieldError("form", "email", "Incorrect Login or Password"));
        model.addAttribute("form", form);
        return "registration";
    }

    private UserDTO currentUser() {
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        return serviceUser.getUserById(user.getId());
    }
}