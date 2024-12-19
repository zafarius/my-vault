package vault.webservice.file;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import vault.domain.common.SecurityRoles;
import vault.domain.file.FileService;
import vault.file.model.ResponsePageVault;
import vault.file.model.SearchRequestDTO;
import vault.webservice.contracts.file.FilesApi;
import java.io.IOException;
import java.util.Base64;
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
    public ResponseEntity<ResponsePageVault> getFiles(final UUID accountId, final SearchRequestDTO searchRequestDTO) {
        val vaultResponseDTO = fileService.getZippedContent(
                accountId,
                fileControllerMapper.map(searchRequestDTO)
        );
        val contentEncoded = Base64.getEncoder().encodeToString(vaultResponseDTO.getContent());
        val response = new ResponsePageVault(
                vaultResponseDTO.getTotalPages(),
                vaultResponseDTO.getContentSize(),
                contentEncoded
        );

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
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
