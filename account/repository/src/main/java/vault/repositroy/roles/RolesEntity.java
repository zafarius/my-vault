package vault.repositroy.roles;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import vault.repositroy.account.AccountEntity;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(
        schema = "vault",
        name = "roles")
public class RolesEntity {
    @Id
    @Column(name = "role_name", nullable = false, updatable = false)
    @ToString.Include
    private String roleName;

    @ManyToMany(mappedBy = "accountRoles")
    private Set<AccountEntity> accounts;
}