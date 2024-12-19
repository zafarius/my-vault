package vault.webservice.file;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.web.multipart.MultipartFile;
import vault.domain.file.VaultFile;
import vault.domain.file.VaultRequestDTO;

import java.io.IOException;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface FileControllerMapper {
    @Mapping(target = "name", source = "originalFilename")
    @Mapping(target = "type", source = "contentType")
    @Mapping(target = "size", source = "size")
    @Mapping(target = "contentStream", source = "inputStream")
    VaultFile map(MultipartFile multipartFile) throws IOException;

    VaultRequestDTO map(vault.file.model.SearchRequestDTO searchRequestDTO);
}
