package com.pablodev.documentworkspace.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class DocumentResponse extends ItemResponse {
    private String extension;
    private Long length;
    private boolean locked;
    private Long version;
}
