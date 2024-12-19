package vault.domain.file;

import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import lombok.val;
import vault.domain.common.EntityMissingException;

@RequiredArgsConstructor
@Service
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;

    @Override
    public void uploadFile(final UUID accountId, final VaultFile vaultFile) {
       fileRepository.save(accountId, vaultFile);
    }

    @Override
    public VaultResponseDTO getZippedContent(final UUID accountId, final VaultRequestDTO vaultRequestDTO) {
        val pageVaultFile = fileRepository.findByAccountId(accountId, vaultRequestDTO);

        if (pageVaultFile.getContentSize() == 0) {
            throw new EntityMissingException(
                    String.format("Elements not found for search. PageNumber: %s, PageSize: %s, Sort: %s",
                            vaultRequestDTO.getPageNumber(),
                            vaultRequestDTO.getPageSize(),
                            vaultRequestDTO.getSortBy().getValue()
                    )
            );
        }

        pageVaultFile.setContent(zip(pageVaultFile.getVaultFiles()));
        return pageVaultFile;
    }

    private byte[] zip(final List<VaultFile> vaultFiles) {
        val byteArrayOutputStream = new ByteArrayOutputStream();
        try (val zipStream = new ZipOutputStream(byteArrayOutputStream)) {
            vaultFiles.forEach((file -> {
                try {
                    zipStream.putNextEntry(new ZipEntry(file.getName()));
                    IOUtils.copy(file.getContentStream(), zipStream);
                    zipStream.closeEntry();
                    file.getContentStream().close();
                } catch (IOException e) {
                    throw new RuntimeException("Zipping of files failed.", e);
                }
            }));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return byteArrayOutputStream.toByteArray();
    }
}
