package com.pablodev.documentworkspace.services.document;

import com.pablodev.documentworkspace.dto.DocumentContent;
import com.pablodev.documentworkspace.dto.DocumentInfo;
import com.pablodev.documentworkspace.dto.DocumentRequest;
import com.pablodev.documentworkspace.events.DocumentLockEvent;
import com.pablodev.documentworkspace.mappers.DocumentMapper;
import com.pablodev.documentworkspace.model.Document;
import com.pablodev.documentworkspace.repositories.DocumentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
@Transactional
public class DefaultDocumentService implements DocumentService {

    private final DocumentRepository documentRepository;
    private final DocumentMapper documentMapper;
    private final SimpMessagingTemplate messagingTemplate;


    @Override
    public DocumentInfo saveDocument(DocumentRequest documentRequest) {
        Document document = documentMapper.toDocumentEntity(documentRequest);
        Document savedDocument = documentRepository.save(document);
        return documentMapper.toDocumentInfo(savedDocument);
    }

    @Override
    @Transactional(readOnly = true)
    public DocumentContent findDocumentContent(Long id) {
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Document with id: " + id + " not found"));
        return documentMapper.toDocumentContent(document);
    }

    @Override
    @Transactional(readOnly = true)
    public DocumentInfo findDocumentInfo(Long id) {
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Document with id: " + id + " not found"));
        return documentMapper.toDocumentInfo(document);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DocumentInfo> findAllDocumentInfo() {
        return StreamSupport.stream(documentRepository.findAll().spliterator(), false)
                .map(documentMapper::toDocumentInfo)
                .toList();
    }

    @Override
    public void updateDocumentLock(Long id, boolean locked) {
        documentRepository.updateDocumentLock(locked, id);
        messagingTemplate.convertAndSend("/topic/document/", new DocumentLockEvent(id, locked));
    }

    @Override
    public void updateDocumentContent(Long id, byte[] content, boolean lock) {
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Document with id: " + id + " not found"));

        document.setContent(content);
        document.setLocked(lock);
        document.setLength((long) content.length);

        if (!lock) {
            document.setVersion(document.getVersion() + 1);
        }

        documentRepository.save(document);
        messagingTemplate.convertAndSend("/topic/document/", new DocumentLockEvent(id, lock));
    }

    @Override
    public void deleteDocument(Long id) {
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Document with id: " + id + " not found"));

        if (document.isLocked())
            throw new RuntimeException("The document can not be deleted because it is open");

        documentRepository.delete(document);
    }



}
