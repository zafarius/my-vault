package vault.repositroy.account;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface AccountRepository extends CrudRepository<AccountEntity, String> {
    /**
     * Search account table by specific userername.
     *
     * @param username
     * @return @{@link AccountEntity}
     */
    Optional<AccountEntity> findByUsername(String username);
}
