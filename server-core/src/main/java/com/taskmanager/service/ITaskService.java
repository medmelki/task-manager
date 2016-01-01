package com.taskmanager.service;

import com.taskmanager.model.Task;
import org.springframework.stereotype.Service;

@Service
public interface ITaskService extends IGenericService<Task, Integer> {
}
