

package vault.repository.file;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import lombok.val;
import vault.domain.file.VaultFile;

import java.io.FileInputStream;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class FileMapperTest {

    @InjectMocks
    private FileMapperImpl fileMapper;

    @Test
    public void testMapFileToFileEntity() {
        // setup
        val file = new VaultFile(
                "Sample",
                "PNG",
                0L,
                FileInputStream.nullInputStream()
        );

        // when
        val fileEntity = fileMapper.map(file);

        // then
        assertThat(fileEntity.getName()).isEqualTo(file.getName());
        assertThat(fileEntity.getType()).isEqualTo(file.getType());
        assertThat(fileEntity.getSize()).isEqualTo(file.getSize());
    }
}


