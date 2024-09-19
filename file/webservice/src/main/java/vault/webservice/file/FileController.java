package vault.webservice.file;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;
import vault.domain.file.File;
import vault.domain.file.FileService;
import vault.webservice.contracts.file.FilesApi;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import lombok.val;

@Controller
@RequiredArgsConstructor
public class FileController implements FilesApi {
    private final FileService fileService;

    @Override
    public ResponseEntity<Resource> getFiles(final UUID accountId) {

        val resource = new ByteArrayResource(fileService.getZippedContent(accountId));
        return ResponseEntity.ok()
                .contentLength(resource.contentLength())
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition.attachment()
                                .filename("download")
                                .build().toString())
                .body(resource);
    }

    @Override
    public ResponseEntity<Void> uploadFiles(final UUID accountId, final List<MultipartFile> files) {
        files.parallelStream().map((multipartFile) -> {
            try {
                return new File(
                        multipartFile.getOriginalFilename(),
                        multipartFile.getContentType(),
                        multipartFile.getSize(),
                        multipartFile.getInputStream()
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).forEach((file) -> fileService.uploadFile(accountId, file));

        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }
}
