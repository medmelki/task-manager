package com.taskmanager.controller;

import com.taskmanager.model.Task;
import com.taskmanager.model.User;
import com.taskmanager.service.ITaskService;
import com.taskmanager.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TaskController {

    public static final String ROLE_SUPERADMIN = "ROLE_SUPERADMIN";
    @Autowired
    private ITaskService taskService;
    @Autowired
    private IUserService userService;

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPERADMIN')")
    @RequestMapping(value = "/task/", method = RequestMethod.GET)
    public ResponseEntity<List<Task>> listAll() {

        // retrieve the current logged in admin/superadmin
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        // get the User object mapped from the database data
        User user = userService.read(auth.getName());
        List<Task> tasks;
        // check if superadmin then show all tasks
        if (user.getRole().equals(ROLE_SUPERADMIN)) {
            tasks = taskService.findAll();
        } else {
            // else return only user managed tasks
            tasks = user.getTasksToManage();
        }
        if (tasks.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPERADMIN')")
    @RequestMapping(value = "/task/", method = RequestMethod.POST)
    public ResponseEntity<Void> addTask(@RequestBody Task task) {
        putManager(task);
        taskService.create(task);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPERADMIN')")
    @RequestMapping(value = "/task/", method = RequestMethod.PUT)
    public ResponseEntity<Task> updateTask(@RequestBody Task task) {
        putManager(task);
        taskService.update(task);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPERADMIN')")
    @RequestMapping(value = "/task/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Task> deleteTask(@PathVariable String id) {

        Task task = new Task();
        task.setId(Integer.parseInt(id));
        taskService.delete(task);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPERADMIN')")
    @RequestMapping(value = "/task/auto/", method = RequestMethod.POST)
    public ResponseEntity<Task> generateTask(@RequestBody Task task) {
        putManager(task);
        taskService.create(task);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    private void putManager(Task task) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        // get the User object mapped from the database data
        User admin = userService.read(auth.getName());
        task.setManager(admin);
    }
}
