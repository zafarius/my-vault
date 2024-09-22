package vault.domain.file;

import lombok.Data;

import java.io.InputStream;

@Data
public class VaultFile {
    private final String name;
    private final String type;
    private final Long size;
    private final InputStream contentStream;
}
