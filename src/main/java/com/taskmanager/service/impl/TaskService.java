package com.taskmanager.service.impl;


import com.taskmanager.model.User;
import com.taskmanager.service.IDocumentService;
import com.taskmanager.service.ITaskService;
import org.springframework.stereotype.Repository;

@Repository
public class TaskService extends GenericService<User, Integer> implements ITaskService {

    public TaskService() {
        super(User.class);
    }
}
