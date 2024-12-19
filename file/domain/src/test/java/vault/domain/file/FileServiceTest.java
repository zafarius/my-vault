package vault.domain.file;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import vault.domain.common.EntityMissingException;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipInputStream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FileServiceTest {
    @Mock
    private FileRepository fileRepository;

    @InjectMocks
    private  FileServiceImpl fileService;

    @Test
    public void testCreateFile() {
        // setup
        val accountId = UUID.randomUUID();
        val file = new VaultFile(
                "Sample",
                "PNG",
                0L,
                FileInputStream.nullInputStream()
        );

        // when
        doNothing().when(fileRepository).save(accountId, file);
        fileService.uploadFile(accountId, file);

        // then
        verify(fileRepository).save(accountId, file);
    }

    @Test
    public void testGetFilesPaged() throws IOException {
        // setup
        val accountId = UUID.randomUUID();
        byte[] bytes1 = "hello".getBytes();
        val file1 = new VaultFile(
                "Sample1",
                "txt",
                Integer.toUnsignedLong(bytes1.length),
                new ByteArrayInputStream(bytes1)
        );
        byte[] bytes2 = "bye".getBytes();
        val file2 = new VaultFile(
                "Sample2",
                "txt",
                Integer.toUnsignedLong(bytes2.length),
                new ByteArrayInputStream(bytes2)
        );

        val page = new VaultRequestDTO(
                1,
                2,
                VaultRequestDTO.Sort.CREATED_DATE
        );

        // when
        when(fileRepository.findByAccountId(accountId, page)).thenReturn(
                new VaultResponseDTO(
                        1,
                        2,
                        List.of(file1, file2)
                )
        );

        val pageVaultFile = fileService.getZippedContent(accountId, page);
        val zipInput = new ZipInputStream(new ByteArrayInputStream(pageVaultFile.getContent()));

        // then
        //get first entry
        zipInput.getNextEntry();
        assertThat(Arrays.toString(zipInput.readAllBytes())).isEqualTo(Arrays.toString(bytes1));

        //get second entry
        zipInput.getNextEntry();
        assertThat(Arrays.toString(zipInput.readAllBytes())).isEqualTo(Arrays.toString(bytes2));
        verify(fileRepository).findByAccountId(accountId, page);
    }


    @Test
    public void testGetFilesNoResult_throwsException() {
        // setup
        val accountId = UUID.randomUUID();
        val vaultRequestDTO = new VaultRequestDTO(
                2,
                3,
                VaultRequestDTO.Sort.CREATED_DATE
        );

        // when
        when(fileRepository.findByAccountId(accountId, vaultRequestDTO)).thenReturn(
                new VaultResponseDTO(
                        0,
                        0,
                        List.of()
                )
        );

        // then
        assertThrows(
                EntityMissingException.class,
                () -> fileService.getZippedContent(accountId, vaultRequestDTO)
        );

    }
}
