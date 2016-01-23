package com.taskmanager.controller;

import com.taskmanager.model.Task;
import com.taskmanager.service.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TaskController {

    @Autowired
    private ITaskService taskService;

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPERADMIN')")
    @RequestMapping(value = "/task/", method = RequestMethod.GET)
    public ResponseEntity<List<Task>> listAll() {

        List<Task> tasks = taskService.findAll();
        if (tasks.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPERADMIN')")
    @RequestMapping(value = "/task/", method = RequestMethod.POST)
    public ResponseEntity<Void> addTask(@RequestBody Task task) {
        taskService.create(task);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPERADMIN')")
    @RequestMapping(value = "/task/", method = RequestMethod.PUT)
    public ResponseEntity<Task> updateTask(@RequestBody Task task) {

        taskService.update(task);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPERADMIN')")
    @RequestMapping(value = "/task/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Task> deleteTask(@PathVariable String id) {

        Task task = new Task();
        task.setId(Integer.parseInt(id));
        taskService.delete(task);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
