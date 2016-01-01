package com.taskmanager.service;

import com.taskmanager.model.Document;
import org.springframework.stereotype.Service;

@Service
public interface IDocumentService extends IGenericService<Document, Integer> {
}
