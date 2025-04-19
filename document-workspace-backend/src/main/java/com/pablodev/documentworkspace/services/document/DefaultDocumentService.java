package com.pablodev.documentworkspace.services.document;

import com.pablodev.documentworkspace.dto.document.DocumentContentResponse;
import com.pablodev.documentworkspace.dto.document.DocumentFilterRequest;
import com.pablodev.documentworkspace.dto.document.DocumentRequest;
import com.pablodev.documentworkspace.dto.document.DocumentResponse;
import com.pablodev.documentworkspace.events.DocumentLockEvent;
import com.pablodev.documentworkspace.mappers.DocumentMapper;
import com.pablodev.documentworkspace.model.Document;
import com.pablodev.documentworkspace.model.Folder;
import com.pablodev.documentworkspace.model.Type;
import com.pablodev.documentworkspace.repositories.DocumentRepository;
import com.pablodev.documentworkspace.repositories.FolderRepository;
import com.pablodev.documentworkspace.repositories.TypeRepository;
import com.pablodev.documentworkspace.utils.DocumentUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
@Transactional
public class DefaultDocumentService implements DocumentService {

    private final FolderRepository folderRepository;
    private final DocumentRepository documentRepository;
    private final TypeRepository typeRepository;
    private final DocumentMapper documentMapper;
    private final SimpMessagingTemplate messagingTemplate;
    private final DocumentUtils documentUtils;

    @Override
    public DocumentResponse saveDocument(DocumentRequest documentRequest) throws IOException {
        String extensionName = documentUtils.getExtensionFromMultipart(documentRequest.getFile());

        Folder folder = folderRepository.findById(documentRequest.getFolderId())
                .orElseThrow(() -> new EntityNotFoundException("Folder not found: " + documentRequest.getFolderId()));

        Type type = typeRepository.findTypeByExtension(extensionName)
                .orElseGet(() -> typeRepository.findByName("other")
                        .orElseThrow(() -> new EntityNotFoundException("Type not found")));

        Document document = documentMapper.toDocumentEntity(documentRequest.getFile());
        document.setType(type);
        document.setFolder(folder);
        folder.getDocuments().add(document);

        Document savedDocument = documentRepository.save(document);
        return documentMapper.toDocumentResponse(savedDocument);
    }

    @Override
    @Transactional(readOnly = true)
    public DocumentContentResponse findDocumentContent(Long documentId) {
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new EntityNotFoundException("Document with id: " + documentId + " not found"));
        return documentMapper.toDocumentContent(document);
    }

    @Override
    @Transactional(readOnly = true)
    public DocumentResponse findDocumentInfo(Long documentId) {
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new EntityNotFoundException("Document with id: " + documentId + " not found"));
        return documentMapper.toDocumentResponse(document);
    }


    @Override
    @Transactional(readOnly = true)
    public List<DocumentResponse> findAllDocumentInfo() {
        return StreamSupport.stream(documentRepository.findAll().spliterator(), false)
                .map(documentMapper::toDocumentResponse)
                .toList();
    }

    @Override
    public void updateDocumentLock(Long documentId, boolean locked) {
        documentRepository.updateDocumentLock(locked, documentId);
        messagingTemplate.convertAndSend("/topic/document/", new DocumentLockEvent(documentId, locked));
    }

    @Override
    public void updateDocumentContent(Long documentId, byte[] content, boolean lock) {
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new EntityNotFoundException("Document with id: " + documentId + " not found"));

        document.setContent(content);
        document.setLocked(lock);
        document.setLength((long) content.length);

        if (!lock) {
            document.setVersion(document.getVersion() + 1);
        }

        documentRepository.save(document);
        messagingTemplate.convertAndSend("/topic/document/", new DocumentLockEvent(documentId, lock));
    }

    @Override
    public void deleteDocument(Long documentId) {
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new EntityNotFoundException("Document with id: " + documentId + " not found"));

        if (document.isLocked())
            throw new RuntimeException("The document can not be deleted because it is open");

        documentRepository.delete(document);
    }

    @Override
    public List<DocumentResponse> findDocumentsByFilters(DocumentFilterRequest documentFilterRequest) {
        List<Document> filteredDocuments = documentRepository.findDocumentsByFilter(
                documentFilterRequest.getFolderId(),
                documentFilterRequest.getFilename(),
                documentFilterRequest.getTypes()
        );
        return filteredDocuments.stream().map(documentMapper::toDocumentResponse).toList();
    }


}
