package ru.khmelevskoy.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.khmelevskoy.api.converter.ServiceCategoryToResponseConverter;
import ru.khmelevskoy.api.json.CategoryRequest;
import ru.khmelevskoy.api.json.CategoryResponse;
import ru.khmelevskoy.dto.CategoryDTO;
import ru.khmelevskoy.dto.UserDTO;
import ru.khmelevskoy.securitry.CustomUserDetails;
import ru.khmelevskoy.service.ServiceCategory;
import ru.khmelevskoy.service.ServiceUser;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class ServiceCategoryController extends Controller {

    private final ServiceCategory serviceCategory;

    private final ServiceCategoryToResponseConverter converter;

    private final ServiceUser serviceUser;

    @PostMapping("/insert-category")
    public ResponseEntity<HttpStatus> insertCategory(@RequestBody @Valid CategoryRequest request) {
        Long userId = currentUser().getId();
       serviceCategory.createCategory(userId, request.getCategoryName(), request.getCategoryGroup());

            return status(HttpStatus.ACCEPTED).build();

    }

    @GetMapping("/find-categories")
    public ResponseEntity<List<CategoryResponse>> getCategories() {
        Long userId = currentUser().getId();
        List<CategoryDTO> categories = serviceCategory.findCategories(userId);
        return ok(categories.stream()
                .map(converter::convert)
                .collect(Collectors.toList()));
    }

    private UserDTO currentUser() {
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        return serviceUser.getUserById(user.getId());
    }

}