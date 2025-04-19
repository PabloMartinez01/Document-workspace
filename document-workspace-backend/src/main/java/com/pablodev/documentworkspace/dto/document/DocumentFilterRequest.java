package com.pablodev.documentworkspace.dto.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentFilterRequest {
    private Long folderId;
    private String filename;
    private List<String> types;
}
