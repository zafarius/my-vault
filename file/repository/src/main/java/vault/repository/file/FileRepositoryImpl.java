package vault.repository.file;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;
import vault.domain.file.VaultRequestDTO;
import vault.domain.file.VaultResponseDTO;
import vault.domain.file.VaultFile;
import vault.domain.file.FileRepository;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FileRepositoryImpl implements FileRepository {
    private final FileRepositoryJpa fileRepositoryJpa;
    private final PaginationFileRepositoryJpa paginationFileRepositoryJpa;

    private final FileMapper fileMapper;

    @Value("${files.output_dir}")
    private String filesDir;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public void save(final UUID accountId, final VaultFile vaultFile) {
        val fileEntity = fileMapper.map(vaultFile);
        val fileDest = saveAsFile(accountId, vaultFile.getContentStream(), fileEntity.getName());

        fileEntity.setAccountId(accountId);
        fileEntity.setPath(fileDest);
        fileRepositoryJpa.save(fileEntity);
    }

    @Override
    public List<VaultFile> findByAccountId(final UUID accountId) {
        return fileRepositoryJpa.
                findByAccountId(accountId)
                .stream()
                .map(this::toFile)
                .toList();
    }

    @Override
    public VaultResponseDTO findByAccountId(final UUID accountId, final VaultRequestDTO vaultRequestDTO) {
        val pageable = PageRequest.of(
                vaultRequestDTO.getPageNumber(),
                vaultRequestDTO.getPageSize(),
                Sort.by(vaultRequestDTO.getSortBy().getValue()).ascending()
        );

        val page = paginationFileRepositoryJpa.findByAccountId(
                accountId,
                pageable
        );
        val content = page.stream()
                .map(this::toFile)
                .toList();

        return new VaultResponseDTO(
                page.getTotalPages(),
                page.getNumberOfElements(),
                content
        );
    }

    private String saveAsFile(final UUID accountId, final InputStream fileContent, final String fileName) {
        val userDir = filesDir + "/" + accountId;
        val outputFileName = String.format("%s/%s",
                userDir,
                fileName
        );

        try {
            Files.createDirectories(Paths.get(userDir));
            FileCopyUtils.copy(fileContent, Files.newOutputStream(Paths.get(outputFileName)));
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }

        return outputFileName;
    }

    private VaultFile toFile(final VaultFileEntity vaultFileEntity) {
        VaultFile vaultFile;

        try {
            val contentStream = new FileInputStream(ResourceUtils.getFile(vaultFileEntity.getPath()));
            vaultFile = new VaultFile(
                    vaultFileEntity.getName(),
                    vaultFileEntity.getType(),
                    vaultFileEntity.getSize(),
                    contentStream
            );
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }

        return vaultFile;
    }
}
