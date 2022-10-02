package com.omctt.controller;

import com.omctt.dto.ToDoDto;
import com.omctt.entity.ToDo;
import com.omctt.entity.User;
import com.omctt.service.ToDoService;
import com.omctt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class AppController {

    @Autowired
    private ToDoService toDoService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String getToDos(Model model){

        return findPaginated(1, model);
    }

    @GetMapping("/searchByTitle")
    public String getToDosByTitle(Model model,String title){

        if(title !=null && !title.equals("")){
            model.addAttribute("listToDos", toDoService.findByTitle(title));
        } else {
            model.addAttribute("listToDos", toDoService.findAll());
        }

        return "index";
    }

    @GetMapping("/searchByUsername")
    public String getToDosByUsername(Model model,String username){

        if(username !=null && !username.equals("")){
            model.addAttribute("listToDos", toDoService.findByUsername(username));
        } else {
            model.addAttribute("listToDos", toDoService.findAll());
        }

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
    public String save(@Valid @ModelAttribute ToDo toDo, BindingResult result, Model model){
        List<User> userList = userService.getAll();
        if(result.hasErrors()){
            model.addAttribute("title", "New ToDo");
            model.addAttribute("toDo", toDo);
            model.addAttribute("users", userList);
            return "formToDo";
        }
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

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer idToDo){

        toDoService.delete(idToDo);

        return "redirect:/";
    }

    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo, Model model){
        final int pageSize = 10;
        Page<ToDoDto> page = toDoService.findPaginated(pageNo, pageSize);
        List<ToDoDto> toDoDtoList = page.getContent();
        if (page.getTotalPages() > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, page.getTotalPages())
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listToDos", toDoDtoList);
        return "index";
    }
}
