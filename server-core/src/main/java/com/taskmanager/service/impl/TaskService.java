package com.taskmanager.service.impl;


import com.taskmanager.model.Task;
import com.taskmanager.service.ITaskService;
import org.springframework.stereotype.Repository;

@Repository
public class TaskService extends GenericService<Task, Integer> implements ITaskService {

    public TaskService() {
        super(Task.class);
    }
}
