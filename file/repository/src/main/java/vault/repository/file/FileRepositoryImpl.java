package vault.repository.file;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;
import vault.domain.file.File;
import vault.domain.file.FileRepository;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FileRepositoryImpl implements FileRepository {
    private final FileRepositoryJpa fileRepositoryJpa;
    private final FileMapper fileMapper;

    @Value("${files.output_dir}")
    private String filesDir;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public void save(final UUID accountId, final File file) {
        val fileDest = saveAsFile(accountId, file.getContentStream(), file.getName());

        val fileEntity = fileMapper.map(file);
        fileEntity.setAccountId(accountId);
        fileEntity.setPath(fileDest);
        fileRepositoryJpa.save(fileEntity);
    }

    @Override
    public List<File> findByAccountId(final UUID accountId) {
        return fileRepositoryJpa.
                findByAccountId(accountId)
                .stream()
                .map(this::toFile)
                .toList();
    }

    private String saveAsFile(final UUID accountId, final InputStream fileContent, final String fileName) {
        val userDir = filesDir + "/" + accountId;
        val outputFileName = String.format("%s/%s+%s",
                userDir,
                fileName,
                ZonedDateTime.now().format(DateTimeFormatter.ISO_INSTANT)
        );

        try {
            Files.createDirectories(Paths.get(userDir));
            FileCopyUtils.copy(fileContent, Files.newOutputStream(Paths.get(outputFileName)));
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }

        return outputFileName;
    }

    private File toFile(final FileEntity fileEntity) {
        File file;

        try {
            val contentStream = new FileInputStream(ResourceUtils.getFile(fileEntity.getPath()));
            file = new File(
                    fileEntity.getName(),
                    fileEntity.getType(),
                    fileEntity.getSize(),
                    contentStream
            );
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }

        return file;
    }
}
