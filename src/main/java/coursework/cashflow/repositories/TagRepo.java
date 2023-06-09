package coursework.cashflow.repositories;

import org.springframework.data.repository.CrudRepository;
import coursework.cashflow.models.dao.Tag;

import java.util.List;

public interface TagRepo extends CrudRepository<Tag, Long> {
    Tag findById(Integer id);
    List<Tag> findAll();
}
