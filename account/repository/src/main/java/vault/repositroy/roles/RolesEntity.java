/**
 * Entity class that represents roles table
 */
package vault.repositroy.roles;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import vault.repositroy.account.AccountEntity;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(
        schema = "vault",
        name = "roles")
public final class RolesEntity {

    @Id
    @NotNull
    @Column(name = "role_name", nullable = false, updatable = false)
    @ToString.Include
    private String roleName;

    @ManyToMany(mappedBy = "accountRoles")
    private Set<AccountEntity> accounts;
}
