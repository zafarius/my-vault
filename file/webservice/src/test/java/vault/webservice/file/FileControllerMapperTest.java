package vault.webservice.file;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import vault.file.model.SearchRequestDTO;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class FileControllerMapperTest {

    private final FileControllerMapperImpl fileControllerMapper =
            new FileControllerMapperImpl();

    @Test
    public void testMapMultipartFileToVaultFile() throws IOException {
        // setup
        val multipartFile = new MockMultipartFile(
                "files",
                "123",
                MediaType.TEXT_PLAIN_VALUE,
                "qwe".getBytes()
        );

        // when
        val vaultFile = fileControllerMapper.map(multipartFile);

        // then
        assertThat(vaultFile.getContentStream().readAllBytes()).isEqualTo(multipartFile.getInputStream().readAllBytes());
        assertThat(vaultFile.getSize()).isEqualTo(multipartFile.getSize());
        assertThat(vaultFile.getName()).isEqualTo(multipartFile.getOriginalFilename());
        assertThat(vaultFile.getType()).isEqualTo(multipartFile.getContentType());
    }

    @Test
    public void testMapSearchRequestDTOToVaultRequestDTO() {
        // setup
        val searchRequestDTO = new vault.file.model.SearchRequestDTO(
                0,
                1,
                SearchRequestDTO.SortByEnum.CREATED_DATE
        );

        // when
        val vaultRequestDTO = fileControllerMapper.map(searchRequestDTO);

        // then
        assertThat(vaultRequestDTO.getPageNumber()).isEqualTo(searchRequestDTO.getPageNumber());
        assertThat(vaultRequestDTO.getPageSize()).isEqualTo(searchRequestDTO.getPageSize());
        assertThat(vaultRequestDTO.getSortBy().toString()).isEqualTo(searchRequestDTO.getSortBy().toString());
    }
}
