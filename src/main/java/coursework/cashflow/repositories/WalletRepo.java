package coursework.cashflow.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import coursework.cashflow.models.dao.Wallet;

import java.util.List;
import java.util.Optional;

public interface WalletRepo extends CrudRepository<Wallet, Long> {
    List<Wallet> findAll();
    List<Wallet> findByCurrency_Id(int currencyId);
    List<Wallet> findByUser_Id(int userId);
    Optional<Wallet> findById(Integer id);
    void deleteById(int id);
//    Optional<Wallet> findByName(String name);
}
