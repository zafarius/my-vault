package vault.repositroy.account;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import vault.repositroy.roles.RolesEntity;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(
        schema = "vault",
        name = "account")
public class AccountEntity {
    @NotNull
    @Id
    @Column(nullable = false, updatable = false)
    @ToString.Include
    private String username;

    @Min(0)
    @Max(Long.MAX_VALUE)
    @Version
    @Column(nullable = false)
    @ToString.Include
    private long version;

    @NotNull
    @Column(nullable = false)
    private String password;

    @NotNull
    @ManyToMany
    @JoinTable(
            schema = "vault",
            name = "account_roles",
            joinColumns = @JoinColumn(name = "username"),
            inverseJoinColumns = @JoinColumn(name = "role_name"))
    private Set<RolesEntity> accountRoles;
}
