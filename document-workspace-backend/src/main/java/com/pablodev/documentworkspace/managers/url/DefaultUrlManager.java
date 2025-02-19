package com.pablodev.documentworkspace.managers.url;

import org.springframework.stereotype.Component;

@Component
public class DefaultUrlManager implements UrlManager {

    //TODO move to properties

    @Override
    public String getDocumentUrl(Long documentId) {
        return "http://localhost:8080/document/" + documentId;
    }

    @Override
    public String getDocumentCallback() {
        return "http://localhost:8080/document/callback";
    }
}
