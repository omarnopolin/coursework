package coursework.cashflow.repositories;

import org.springframework.data.repository.CrudRepository;
import coursework.cashflow.models.dao.Users;

import java.util.List;

public interface UserRepo extends CrudRepository<Users, Long> {
    Users findByLogin(String login);
    Users findById(Integer id);
    Users findByLoginAndValidationStatus(String login, int status);
    List<Users> findByToken(String token);
}
