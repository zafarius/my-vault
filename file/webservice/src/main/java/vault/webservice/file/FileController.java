package vault.webservice.file;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import vault.domain.common.SecurityRoles;
import vault.domain.file.FileService;
import vault.webservice.contracts.file.FilesApi;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import lombok.val;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class FileController implements FilesApi {
    private final FileService fileService;
    private final FileControllerMapper fileControllerMapper;

    @Override
    @Secured(SecurityRoles.USER)
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
    @Secured(SecurityRoles.USER)
    public ResponseEntity<Void> uploadFiles(final UUID accountId, final List<MultipartFile> files) {
        files.parallelStream().map((multipartFile) -> {
            try {
                return fileControllerMapper.map(multipartFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).forEach((file) ->
                fileService.uploadFile(accountId, file)
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }
}
