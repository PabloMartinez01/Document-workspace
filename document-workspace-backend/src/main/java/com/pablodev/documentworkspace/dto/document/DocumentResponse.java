package com.pablodev.documentworkspace.dto.document;

import com.pablodev.documentworkspace.dto.folder.FolderInfoResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class DocumentResponse {
    private Long id;
    private String filename;
    private String extension;
    private Long length;
    private boolean locked;
    private String type;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private Long version;
    private FolderInfoResponse folder;
}
