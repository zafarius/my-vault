package vault.domain.file;

import java.util.UUID;

public interface FileService {
    void uploadFile(UUID accountId, File file);
    byte[] getZippedContent(UUID accountId);

    //Account createAccount(Account account);
}
