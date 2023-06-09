package coursework.cashflow.repositories;

import org.springframework.data.repository.CrudRepository;
import coursework.cashflow.models.dao.Category;

import java.util.List;

public interface CategoryRepo extends CrudRepository<Category, Long> {
    List<Category> findAll();
    Category findById(Integer id);
}
