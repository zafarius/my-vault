package vault.domain.file;

import lombok.Data;

import java.util.List;

@Data
public class VaultResponseDTO {
    private final int totalPages;
    private final int contentSize;
    private final List<VaultFile> vaultFiles;
    private byte[] content;
}
