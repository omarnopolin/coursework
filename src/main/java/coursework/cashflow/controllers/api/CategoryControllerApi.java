package coursework.cashflow.controllers.api;

import coursework.cashflow.repositories.CategoryRepo;
import coursework.cashflow.repositories.TagRepo;
import coursework.cashflow.services.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import coursework.cashflow.models.dao.Category;
import org.springframework.web.bind.annotation.*;
import coursework.cashflow.models.dto.CategoriesTagsDTO;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryControllerApi {

    private final CategoryService categoryService;

    @GetMapping("/get")
    public CategoriesTagsDTO getCategoriesAngTags() {
        return categoryService.getCategoriesAngTags();
    }

    @PostMapping("/create")
    public void createCategory(@RequestBody Category category) {
        categoryService.createCategory(category);
    }

    @PostMapping("/update")
    public void updateCategory(@RequestBody Category newCategory) {
        categoryService.updateCategory(newCategory);
    }

    @PostMapping("/delete")
    public void deleteCategory(@RequestBody Category category) {
        categoryService.deleteCategory(category);
    }
}
