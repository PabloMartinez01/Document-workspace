package com.pablodev.documentworkspace.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class DocumentResponse {

    private Long id;

    private String filename;

    private String extension;

    private Long length;

    private byte[] content;

}
