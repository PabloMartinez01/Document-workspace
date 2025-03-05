package com.pablodev.documentworkspace.dto.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class DocumentInfo {

    private Long id;

    private String filename;

    private String extension;

    private Long length;

    private boolean locked;

    private Long version;

}
