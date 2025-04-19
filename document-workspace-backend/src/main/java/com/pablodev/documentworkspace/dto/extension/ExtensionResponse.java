package com.pablodev.documentworkspace.dto.extension;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExtensionResponse {
    private String name;
    private String type;
    private List<String> actions;
}
