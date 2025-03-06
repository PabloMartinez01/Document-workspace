package com.pablodev.documentworkspace.dto.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@Builder
public class DocumentRequest {
    private MultipartFile file;
    private Long folderId;
}
