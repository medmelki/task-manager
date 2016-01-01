package com.taskmanager.controller;

import com.taskmanager.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class UserController {

//    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private IRoleService userService;

    //    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity<List<String>> listAll() {
        ArrayList<String> users = new ArrayList<>();
        users.addAll(Arrays.asList(new String[]{"test", "test2"}));
        //users = userService.findAll();
        return new ResponseEntity<List<String>>(users, HttpStatus.OK);
    }

/*    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPERADMIN')")
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String addUser(@ModelAttribute User user) {
        userService.create(user);
        return "redirect:/user";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPERADMIN')" )
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public String updateUser(@ModelAttribute User user) {
        userService.update(user);
        return "redirect:/user";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPERADMIN')")
    @RequestMapping(value = "remove", method = RequestMethod.POST)
    public String deleteUser(@ModelAttribute User user) {
        userService.delete(user);
        return "redirect:/user";
    }*/


}
