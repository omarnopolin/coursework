package coursework.cashflow.services;

import coursework.cashflow.models.dao.Category;
import coursework.cashflow.models.dto.CategoriesTagsDTO;
import coursework.cashflow.repositories.CategoryRepo;
import coursework.cashflow.repositories.TagRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService {

    private final CategoryRepo categoryRepo;

    private final TagRepo tagRepo;

    @GetMapping("/get")
    public CategoriesTagsDTO getCategoriesAngTags() {
        return CategoriesTagsDTO.builder()
                .categories(categoryRepo.findAll())
                .tags(tagRepo.findAll())
                .build();
    }

    @Transactional
    public void createCategory(@RequestBody Category category) {
        log.info("CREATE CATEGORY");
        categoryRepo.save(category);
    }

    @PostMapping("/update")
    public void updateCategory(@RequestBody Category newCategory) {
        log.info("UPDATE CATEGORY");
        Category category = categoryRepo.findById(newCategory.getId());
        category.setName(newCategory.getName().equals("") ? category.getName() : newCategory.getName());
//            category.setIcon(newCategory.getIcon() == null ? category.getIcon() : newCategory.getIcon());
        category.setDescription(newCategory.getDescription().equals("") ? category.getDescription() : newCategory.getDescription());
        categoryRepo.save(category);
    }

    @PostMapping("/delete")
    public void deleteCategory(@RequestBody Category category) {
        log.info("DELETE CATEGORY");
        categoryRepo.delete(category);
    }

}
