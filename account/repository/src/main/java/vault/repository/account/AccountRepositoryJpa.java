package vault.repository.account;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface AccountRepositoryJpa extends CrudRepository<AccountEntity, String> {
    /**
     * Search account table by specific username.
     *
     * @param username
     * @return @{@link AccountEntity}
     */
    Optional<AccountEntity> findByUsername(String username);
}
