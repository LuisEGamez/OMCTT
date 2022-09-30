package com.omctt.controller;

import com.omctt.service.ToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {

    @Autowired
    private ToDoService toDoService;

    @GetMapping("/")
    public String viewHomePage(Model model){

        model.addAttribute("listToDos", toDoService.findAll());
        return "index";
    }
}
