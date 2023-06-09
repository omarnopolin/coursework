package coursework.cashflow.services;

import coursework.cashflow.models.UsersDetails;
import coursework.cashflow.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import coursework.cashflow.models.dao.Users;

public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UserRepo userRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        Users user = userRepository.findByLogin(username);
        if (user == null || user.getValidationStatus() == 0) {
            throw new UsernameNotFoundException("Could not find user");
        }

        return new UsersDetails(user);
    }
}