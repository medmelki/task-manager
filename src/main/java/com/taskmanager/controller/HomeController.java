package com.taskmanager.controller;

import com.taskmanager.model.Person;
import com.taskmanager.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private PersonService personSvc;

    @RequestMapping(method = RequestMethod.GET)
    public String listAll(Model model) {
        model.addAttribute("persons", personSvc.getAll());
        return "home";
    }

    @RequestMapping(value = "/addPerson", method = RequestMethod.POST)
    public String addPerson(@ModelAttribute Person person) {
        personSvc.add(person);
        return "redirect:/";
    }
}
