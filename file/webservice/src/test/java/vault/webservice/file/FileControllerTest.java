
package vault.webservice.file;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import vault.domain.file.FileService;
import lombok.val;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;

@WebMvcTest
@ContextConfiguration(classes = FileControllerConfiguration.class)

public class FileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FileService fileService;

    @Test
    void whenCreateFiles_ThenStatus201() throws Exception {
        // setup
        val accountId = UUID.randomUUID();
        val multipartFile1 = new MockMultipartFile(
                "files",
                "123",
                MediaType.TEXT_PLAIN_VALUE,
                "qwe".getBytes()
        );

        // then
        mockMvc.perform(
                multipart("/account/{accountId}/file", accountId.toString())
                        .file(multipartFile1)
        )
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(MockMvcResultMatchers.content().string(""));


        verify(fileService).uploadFile(eq(accountId), any());
    }

    @Test
    void whenGetFiles_ThenStatus200() throws Exception {
        // setup
        val response = "Hallo".getBytes();
        val accountId = UUID.randomUUID();

        // when
        when(fileService.getZippedContent(accountId)).thenReturn(response);

        // then
        mockMvc.perform(
                        get("/account/{accountId}/file", accountId.toString())
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().bytes(response))
                .andExpect(MockMvcResultMatchers.header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"download\""))
                .andExpect(MockMvcResultMatchers.content().contentType("application/zip"));


        verify(fileService).getZippedContent(accountId);
    }
}
