package com.taskmanager.controller;

import com.taskmanager.model.Hach;
import com.taskmanager.service.IHachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;

@Controller
public class HachController {

    @Autowired
    private IHachService hachService;

    @RequestMapping(value = "/generated/{hach}", method = RequestMethod.GET)
    public ModelAndView getTaskPage(@PathVariable String hach)
            throws ServletException, IOException {

        if (hach != null) {
            Hach hachObject = new Hach(hach);
            List<Hach> hachs = hachService.findAll();
            // if hash is in database
            for (Hach hachElt : hachs) {
                if (hachElt.getHach().equals(hach)) {
                    ModelAndView modelAndView = new ModelAndView("tasks");
                    modelAndView.addObject("hach", hach);
                    return new ModelAndView("tasks");
                }
            }
        }
        return new ModelAndView("access/denied");
    }

}

