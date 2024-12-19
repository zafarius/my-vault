package vault.domain.file;

import lombok.Data;
import lombok.Getter;

@Data
public class VaultRequestDTO {
    private final int pageSize;
    private final int pageNumber;
    private final Sort sortBy;

    @Getter
    public enum Sort {
        CREATED_DATE("createdDate");
        private final String value;

        Sort(final String columnName) {
            this.value = columnName;
        }
    }
}
