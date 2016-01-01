package com.taskmanager.service;

import com.taskmanager.model.Note;
import org.springframework.stereotype.Service;

@Service
public interface INoteService extends IGenericService<Note, Integer> {
}
