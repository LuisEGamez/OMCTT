package com.omctt.controller;

import com.omctt.dto.ToDoDto;
import com.omctt.entity.ToDo;
import com.omctt.entity.User;
import com.omctt.service.ToDoService;
import com.omctt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AppController {

    @Autowired
    private ToDoService toDoService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String viewHomePage(Model model){

        model.addAttribute("listToDos", toDoService.findAll());
        return "index";
    }

    @GetMapping("/create")
    public String create(Model model){
        ToDo toDo = new ToDo();
        List<User> userList = userService.getAll();
        model.addAttribute("title", "New ToDo");
        model.addAttribute("toDo", toDo);
        model.addAttribute("users", userList);

        return "formToDo";

    }

    @PostMapping("/save")
    public String save(@ModelAttribute ToDo toDo){

        toDoService.saveToDo(toDo);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer idToDo, Model model){
        ToDo toDo = toDoService.findById(idToDo);
        List<User> userList = userService.getAll();
        model.addAttribute("title", "Edit ToDo");
        model.addAttribute("toDo", toDo);
        model.addAttribute("users", userList);

        return "formToDo";

    }
}
