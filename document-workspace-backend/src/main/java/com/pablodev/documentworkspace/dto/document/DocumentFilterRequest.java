package com.pablodev.documentworkspace.dto.document;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentFilterRequest {

    @NotNull
    @Min(1)
    private Long folderId;

    @NotNull
    private String filename;

    @NotNull
    private List<String> types;
}
