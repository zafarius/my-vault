package vault.repositroy.account;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Repository
public interface AccountRepository extends CrudRepository<AccountEntity, String> {
    Optional<AccountEntity> findByUsername(String username);
}
