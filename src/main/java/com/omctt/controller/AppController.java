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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class AppController {

    @Autowired
    private ToDoService toDoService;

    @Autowired
    private UserService userService;

    @GetMapping("/index")
    public String index(@RequestParam(value = "page") Optional<Integer> page,
                           Model model){
        int currentPage = page.orElse(1);
        Page<ToDoDto> toDoDtos = toDoService.findAll(currentPage);
        List<ToDoDto> toDoDtoList = toDoDtos.getContent();
        model.addAttribute("endPoint", "/index");
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", toDoDtos.getTotalPages());
        model.addAttribute("listToDos", toDoDtoList);
        model.addAttribute("reverseSortDir",  "asc");
        return "index";
    }

    @GetMapping("/sort")
    public String getToDos(@RequestParam(value = "page") Optional<Integer> page,
                           @RequestParam("sortField") Optional<String> sortField,
                           @RequestParam("sortDir") Optional<String> sortDirection,
                           Model model){
        int currentPage = page.orElse(1);
        String currentSortField = sortField.orElse("title");
        String currentSortDirec = sortDirection.orElse("asc");
        List<ToDoDto> toDoDtoList = toDoService.findAll1(currentPage,currentSortField, currentSortDirec);
        int totalPages = toDoService.totalPages();
        model.addAttribute("endPoint", "/sort");
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("listToDos", toDoDtoList);

        model.addAttribute("sortField", currentSortField);
        model.addAttribute("sortDir", currentSortDirec);
        model.addAttribute("reverseSortDir", currentSortDirec.equals("asc") ? "desc" : "asc");
        return "index";
    }

    @GetMapping("/searchByTitle")
    public String getToDosByTitle(@RequestParam(value = "page") Optional<Integer> page,
                                  @RequestParam(value = "title") String title,
                                  @RequestParam("sortField") Optional<String> sortField,
                                  @RequestParam("sortDir") Optional<String> sortDirection,
                                  Model model){
        int currentPage = page.orElse(1);
        String currentSortField = sortField.orElse("title");
        String currentSortDirec = sortDirection.orElse("asc");
        if(title !=null && !title.equals("")){
            Page<ToDoDto> toDoDtos = toDoService.findByTitle(title, currentPage);
            List<ToDoDto> toDoDtoList = toDoDtos.getContent();
            model.addAttribute("endPoint", "/searchByTitle");
            model.addAttribute("title", title);
            model.addAttribute("currentPage", currentPage);
            model.addAttribute("totalPages", toDoDtos.getTotalPages());
            model.addAttribute("listToDos", toDoDtoList);
        } else {
            model.addAttribute("listToDos", toDoService.findAll(currentPage));
        }

        return "byTitle";
    }

    @GetMapping("/searchByUsername")
    public String getToDosByUsername(@RequestParam(value = "page") Optional<Integer> page,
                                     @RequestParam(value = "username") String username,
                                     @RequestParam("sortField") Optional<String> sortField,
                                     @RequestParam("sortDir") Optional<String> sortDirection,
                                     Model model){
        int currentPage = page.orElse(1);
        String currentSortField = sortField.orElse("title");
        String currentSortDirec = sortDirection.orElse("asc");
        if(username !=null && !username.equals("")){
            Page<ToDoDto> toDoDtos = toDoService.findByUsername(username, currentPage);
            List<ToDoDto> toDoDtoList = toDoDtos.getContent();
            model.addAttribute("endPoint", "/searchByUsername");
            model.addAttribute("username", username);
            model.addAttribute("currentPage", currentPage);
            model.addAttribute("totalPages", toDoDtos.getTotalPages());
            model.addAttribute("listToDos", toDoDtoList);

        } else {
            model.addAttribute("listToDos", toDoService.findAll(currentPage));
        }

        return "filterByUsername";
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

    /*@GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo, Model model){

        //Page<ToDoDto> page = toDoService.findPaginated(pageNo);
        List<ToDoDto> toDoDtoList = page.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("listToDos", toDoDtoList);
        return "index";
    }*/

}
