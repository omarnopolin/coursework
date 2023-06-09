package coursework.cashflow.controllers.api;

import coursework.cashflow.repositories.TagRepo;
import coursework.cashflow.services.TagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import coursework.cashflow.models.dao.Tag;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api/tag")
@RequiredArgsConstructor
public class TagControllerApi {
    private final TagService tagService;

    @PostMapping("/create")
    public void createTag(@RequestBody Tag tag) {
        tagService.createTag(tag);
    }

    @PostMapping("/update")
    public void updateTag(@RequestBody Tag newTag) {
        tagService.updateTag(newTag);
    }

    @PostMapping("/delete")
    public void deleteTag(@RequestBody Tag tag) {
        tagService.deleteTag(tag);
    }
}
