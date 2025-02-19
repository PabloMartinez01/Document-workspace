package com.pablodev.documentworkspace.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class DocumentContent {

    private String filename;

    private byte[] content;

}
