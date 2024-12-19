package vault.domain.file;

import java.util.UUID;

public interface FileService {
    void uploadFile(UUID accountId, VaultFile vaultFile);

    VaultResponseDTO getZippedContent(UUID accountId, VaultRequestDTO vaultRequestDTO);
}
