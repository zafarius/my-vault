package vault.repository.file;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.ZonedDateTime;
import java.util.UUID;

@SuppressWarnings("MagicNumber")
@Getter
@Setter
@Entity
@Table(
        schema = "vault",
        name = "file"
)
public class VaultFileEntity {
    @NotNull
    @Id
    @Column(nullable = false, updatable = false)
    @ToString.Include
    private UUID id;

    @NotNull
    @Size(max = 255)
    @Column(nullable = false, updatable = false)
    @ToString.Include
    private String name;

    @NotNull
    @Size(max = 510)
    @Column(nullable = false, updatable = false)
    @ToString.Include
    private String path;

    @NotNull
    @Size(max = 50)
    @Column(nullable = false, updatable = false)
    @ToString.Include
    private String type;

    @Column(name = "created_date", nullable = false, updatable = false)
    private ZonedDateTime createdDate;

    @NotNull
    @Column(nullable = false, updatable = false)
    @ToString.Include
    private Long size;

    @NotNull
    @Column(name = "account_id", nullable = false, updatable = false)
    @ToString.Include
    private UUID accountId;

    @PrePersist
    public void prePersist() {
        if (this.createdDate == null) {
            this.createdDate = ZonedDateTime.now();
        }

        if (this.id == null) {
            this.id = UUID.randomUUID();
        }
    }
}
