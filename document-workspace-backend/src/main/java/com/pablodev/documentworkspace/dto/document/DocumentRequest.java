package com.pablodev.documentworkspace.dto.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class DocumentRequest {

    private String filename;

    private String extension;

    private Long length;

    private byte[] content;

}
