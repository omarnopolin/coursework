package coursework.cashflow.models.dao;

import lombok.Data;
import coursework.cashflow.models.Role;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId", updatable = false, nullable = false)
    private Integer id;
    private String login;
    private String password;
    private String email;
    String token;
    int validationStatus;
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;
}
