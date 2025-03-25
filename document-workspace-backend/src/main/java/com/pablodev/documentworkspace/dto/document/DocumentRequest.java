package com.pablodev.documentworkspace.dto.document;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@Builder
public class DocumentRequest {

    @NotNull
    private MultipartFile file;

    @NotNull
    @Min(1)
    private Long folderId;
}
