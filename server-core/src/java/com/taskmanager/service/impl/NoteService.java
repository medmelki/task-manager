package com.taskmanager.service.impl;


import com.taskmanager.model.User;
import com.taskmanager.service.IDocumentService;
import com.taskmanager.service.INoteService;
import org.springframework.stereotype.Repository;

@Repository
public class NoteService extends GenericService<User, Integer> implements INoteService {

    public NoteService() {
        super(User.class);
    }
}
