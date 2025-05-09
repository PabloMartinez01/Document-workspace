package com.pablodev.documentworkspace.dto.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class ErrorResponse {
    private HttpStatus status;
    private String message;

    @Builder.Default
    private List<String> errors = Collections.emptyList();
}
