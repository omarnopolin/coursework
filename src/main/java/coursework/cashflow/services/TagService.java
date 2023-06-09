package coursework.cashflow.services;

import coursework.cashflow.models.dao.Tag;
import coursework.cashflow.repositories.TagRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepo tagRepo;

    public void createTag(Tag tag) {
        log.info("CREATE TAG");
        tagRepo.save(tag);
    }

    public void updateTag(Tag newTag) {
        log.info("UPDATE TAG");
        Tag tag = tagRepo.findById(newTag.getId());
        tag.setName(newTag.getName().equals("") ? tag.getName() : newTag.getName());
        tag.setDescription(newTag.getDescription().equals("") ? tag.getDescription() : newTag.getDescription());
        tag.setPrice(newTag.getPrice() == null ? tag.getPrice() : newTag.getPrice());
        tagRepo.save(tag);

    }

    public void deleteTag(Tag tag) {
        log.info("DELETE TAG");
        tagRepo.delete(tag);

    }
}
