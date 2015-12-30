package com.taskmanager.controller;

import com.taskmanager.model.User;
import com.taskmanager.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public String listAll(Model model) {
        model.addAttribute("users", userService.findAll());
        return "user";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String addPerson(@ModelAttribute User user) {
        userService.create(user);
        return "redirect:/user";
    }
}
