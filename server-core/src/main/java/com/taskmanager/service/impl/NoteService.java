package com.taskmanager.service.impl;


import com.taskmanager.model.Note;
import com.taskmanager.service.INoteService;
import org.springframework.stereotype.Repository;

@Repository
public class NoteService extends GenericService<Note, Integer> implements INoteService {

    public NoteService() {
        super(Note.class);
    }
}
