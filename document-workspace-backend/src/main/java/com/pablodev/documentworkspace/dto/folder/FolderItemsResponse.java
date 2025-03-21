package com.pablodev.documentworkspace.dto.folder;

import com.pablodev.documentworkspace.dto.document.DocumentInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FolderItemsResponse {
    private List<DocumentInfo> documents;
    private List<FolderInfo> folders;
}
