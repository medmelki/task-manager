package com.taskmanager.service.impl;


import com.taskmanager.model.Document;
import com.taskmanager.service.IDocumentService;
import org.springframework.stereotype.Repository;

@Repository
public class DocumentService extends GenericService<Document, Integer> implements IDocumentService {

    public DocumentService() {
        super(Document.class);
    }
}
