package com.taskmanager.service.impl;


import com.taskmanager.model.User;
import com.taskmanager.service.IDocumentService;
import org.springframework.stereotype.Repository;

@Repository
public class DocumentService extends GenericService<User, Integer> implements IDocumentService {

    public DocumentService() {
        super(User.class);
    }
}
