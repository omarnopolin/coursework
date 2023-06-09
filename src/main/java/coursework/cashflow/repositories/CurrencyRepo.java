package coursework.cashflow.repositories;

import org.springframework.data.repository.CrudRepository;
import coursework.cashflow.models.dao.Currency;

import java.util.List;

public interface CurrencyRepo extends CrudRepository<Currency, Long> {
    Currency findById(Integer id);
    List<Currency> findAll();
}
