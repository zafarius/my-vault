package vault.repository.file;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface PaginationFileRepositoryJpa extends PagingAndSortingRepository<VaultFileEntity, String> {
    Page<VaultFileEntity> findByAccountId(UUID accountId, Pageable pageable);
}
