package vault.repository.file;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import vault.domain.file.VaultFile;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface FileMapper {
    @Mapping(target = "path", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "accountId", ignore = true)
    VaultFileEntity map(VaultFile vaultFile);

    @Mapping(target = "contentStream", ignore = true)
    VaultFile map(VaultFileEntity vaultFileEntity);
}
