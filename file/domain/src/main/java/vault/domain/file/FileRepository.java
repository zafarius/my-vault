package vault.domain.file;

import java.util.List;
import java.util.UUID;

public interface FileRepository {
    void save(UUID accountId, File file);

    List<File> findByAccountId(UUID accountId);
}
