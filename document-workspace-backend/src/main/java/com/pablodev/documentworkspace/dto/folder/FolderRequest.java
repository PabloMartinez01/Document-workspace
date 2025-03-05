package com.pablodev.documentworkspace.dto.folder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FolderRequest {
    private String name;
    private Long parentFolderId;
}
