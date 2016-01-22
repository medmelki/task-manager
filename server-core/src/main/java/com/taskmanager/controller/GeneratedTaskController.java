package com.taskmanager.controller;

import com.taskmanager.model.Hach;
import com.taskmanager.model.Task;
import com.taskmanager.service.IHachService;
import com.taskmanager.service.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/generated/{hach}")
public class GeneratedTaskController {

    @Autowired
    private ITaskService taskService;
    @Autowired
    private IHachService hachService;


    @RequestMapping(value = "/task/", method = RequestMethod.GET)
    public ResponseEntity<List<Task>> listAll(@PathVariable String hach) {

        if (isHachValid(hach)) {
            List<Task> tasks = taskService.findAll();
            if (tasks.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(tasks, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @RequestMapping(value = "/task/", method = RequestMethod.POST)
    public ResponseEntity<Void> addTask(@PathVariable String hach, @RequestBody Task task) {
        if (isHachValid(hach)) {
            taskService.create(task);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @RequestMapping(value = "/task/", method = RequestMethod.PUT)
    public ResponseEntity<Task> updateTask(@PathVariable String hach, @RequestBody Task task) {
        if (isHachValid(hach)) {
            taskService.update(task);
            return new ResponseEntity<>(task, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @RequestMapping(value = "/task/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Task> deleteTask(@PathVariable String hach, @PathVariable String id) {
        if (isHachValid(hach)) {
            Task task = new Task();
            task.setId(Integer.parseInt(id));
            taskService.delete(task);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private boolean isHachValid(String hach) {
        List<Hach> hachs = hachService.findAll();
        // if hash is in database
        for (Hach hachElt : hachs) {
            if (hachElt.getHach().equals(hach)) {
                return true;
            }
        }
        return false;
    }
}
