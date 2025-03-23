package com.pablodev.documentworkspace.dto.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class DocumentContentResponse {
    private String filename;
    private byte[] content;
}
