package com.pablodev.documentworkspace.dto.folder;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FolderRequest {

    @NotEmpty
    @Size(min = 1, max = 100)
    private String name;

    @NotNull
    @Min(1)
    private Long parentFolderId;
}
