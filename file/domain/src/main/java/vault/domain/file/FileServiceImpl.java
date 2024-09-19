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

@RequiredArgsConstructor
@Service
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;

    @Override
    public void uploadFile(final UUID accountId, final File file) {
       fileRepository.save(accountId, file);
    }

    @Override
    public byte[] getZippedContent(final UUID accountId) {
        return zip(fileRepository.findByAccountId(accountId));
    }

    private byte[] zip(final List<File> files) {
        val byteArrayOutputStream = new ByteArrayOutputStream();
        try (val zipStream = new ZipOutputStream(byteArrayOutputStream)) {
            files.forEach((file -> {
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
