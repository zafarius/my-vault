package vault.domain.file;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import vault.VaultApplication;
import org.springframework.test.web.servlet.MockMvc;
import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.UUID;
import java.util.zip.ZipInputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = VaultApplication.class
)
@AutoConfigureMockMvc
public class FileEndToEndTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    void whenCreateFiles_ThenStatus201() throws Exception {
        // setup
        val accountId = UUID.fromString("4b085d51-b364-41b5-a4e2-c25dac3b7a4a");
        val multipartFile1 = new MockMultipartFile(
                "files", "123",
                MediaType.TEXT_PLAIN_VALUE,
                "qwe".getBytes());

        // then
        mockMvc.perform(
                        multipart("/account/{accountId}/file", accountId.toString())
                                .file(multipartFile1)
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string(""));

    }

    @Test
    @WithMockUser
    void whenCreateMultipleFilesAndGetFiles_ThenStatus200() throws Exception {
        // setup
        val accountId = UUID.fromString("4b085d51-b364-41b5-a4e2-c25dac3b7a4a");
        val multipartFile1 = new MockMultipartFile(
                "files", "file1",
                MediaType.TEXT_PLAIN_VALUE,
                "qwe".getBytes());

        val multipartFile2 = new MockMultipartFile(
                "files", "file2",
                MediaType.TEXT_PLAIN_VALUE,
                "qwe".getBytes());

        val multipartFile3 = new MockMultipartFile(
                "files", "file3",
                MediaType.TEXT_PLAIN_VALUE,
                "qwe".getBytes());
        // then
        mockMvc.perform(
                        multipart("/account/{accountId}/file", accountId.toString())
                                .file(multipartFile1)
                                .file(multipartFile2)
                                .file(multipartFile3)
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string(""));

        val result = mockMvc.perform(
                        get("/account/{accountId}/file", accountId.toString())
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"download\""))
                .andExpect(MockMvcResultMatchers.content().contentType("application/zip"))
                .andReturn();

        //unzip
        val bytes = result.getResponse().getContentAsByteArray();
        val zipInput = new ZipInputStream(new ByteArrayInputStream(bytes));

        //get first entry
        zipInput.getNextEntry();
        assertThat(Arrays.toString(zipInput.readAllBytes())).isEqualTo(Arrays.toString(multipartFile1.getBytes()));

        //get second entry
        zipInput.getNextEntry();
        assertThat(Arrays.toString(zipInput.readAllBytes())).isEqualTo(Arrays.toString(multipartFile2.getBytes()));

        //get third entry
        zipInput.getNextEntry();
        assertThat(Arrays.toString(zipInput.readAllBytes())).isEqualTo(Arrays.toString(multipartFile3.getBytes()));
    }
}
