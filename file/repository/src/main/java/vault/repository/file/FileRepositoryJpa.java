package vault.repository.file;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface FileRepositoryJpa extends CrudRepository<FileEntity, String> {
    List<FileEntity> findByAccountId(UUID accountId);
}
