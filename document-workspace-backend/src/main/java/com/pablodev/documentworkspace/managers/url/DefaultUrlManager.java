package com.pablodev.documentworkspace.managers.url;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
public class DefaultUrlManager implements UrlManager {

    @Value("${files.document-service}")
    private String documentServiceUrl;

    @Override
    public String getDocumentUrl(Long documentId) {
       return UriComponentsBuilder.fromUri(URI.create(documentServiceUrl))
                .path("/document/{id}")
                .buildAndExpand(documentId)
                .toUriString();
    }

    @Override
    public String getDocumentCallback(Long documentId) {
        return UriComponentsBuilder.fromUri(URI.create(documentServiceUrl))
                .path("/document/callback/{id}")
                .buildAndExpand(documentId)
                .toUriString();
    }
}
