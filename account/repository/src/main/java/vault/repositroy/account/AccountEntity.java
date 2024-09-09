package vault.repositroy.account;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.Version;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import vault.repositroy.roles.RolesEntity;

import java.time.ZonedDateTime;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(
        schema = "vault",
        name = "account",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "UK_username",
                        columnNames = {"username"})
        })
public class AccountEntity {
    @NotNull
    @Id
    @Column(nullable = false, updatable = false)
    @ToString.Include
    private UUID id;

    @NotNull
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
    @Column(name = "created_date", nullable = false, updatable = false)
    private ZonedDateTime createdDate;

    @NotNull
    @Column(nullable = false)
    private String password;

    @NotNull
    @ManyToMany
    @JoinTable(
            schema = "vault",
            name = "account_roles",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "role_name"))
    private Set<RolesEntity> accountRoles;

    /**
     * Set {@link UUID} for field account id.
     */
    @PrePersist
    public void prePersist() {
        if (this.id == null) {
            this.id = UUID.randomUUID();
        }
    }
}
