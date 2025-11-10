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
import ru.khmelevskoy.dto.CategoryDTO;
import ru.khmelevskoy.dto.UserDTO;
import ru.khmelevskoy.securitry.CustomUserDetails;
import ru.khmelevskoy.service.ServiceCategory;
import ru.khmelevskoy.service.ServiceUser;
import ru.khmelevskoy.web.form.CategoryForm;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class CategoryController {

    private final ServiceCategory serviceCategory;

    private final ServiceUser serviceUser;

    @GetMapping("/insert-category")
    public String getCategory(Model model) {
        model.addAttribute("form", new CategoryForm());
        return "insert-category";
    }

    @PostMapping("/insert-category")
    public String postCategory(@ModelAttribute("form") @Valid CategoryForm form, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            Long userId = currentUser().getId();
            serviceCategory.createCategory(userId, form.getCategoryName(), form.getCategoryGroup());
            model.addAttribute("form", form);
            return "redirect:/show-category";
        }
        result.addError(new FieldError("form", "Category Name", "Fields shouldn't be empty!"));
        model.addAttribute("form", form);
        return "insert-category";
    }

    @GetMapping("/show-category")
    public String geAllCategory(Model model) {
        Long userId = currentUser().getId();
        List<CategoryDTO> categories = serviceCategory.findCategories(userId);
        model.addAttribute("categories", categories);
        return "show-category";
    }

    private UserDTO currentUser() {
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        return serviceUser.getUserById(user.getId());
    }
}