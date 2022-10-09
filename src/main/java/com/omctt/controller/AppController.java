package com.omctt.controller;

import com.omctt.dto.ToDoDto;
import com.omctt.entity.ToDo;
import com.omctt.entity.User;
import com.omctt.service.ToDoService;
import com.omctt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;
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
        List<ToDoDto> toDoDtos = toDoService.findAll(currentPage);
        int totalPages = toDoService.totalPages();
        model.addAttribute("endPoint", "/index");
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("listToDos", toDoDtos);
        model.addAttribute("reverseSortDir",  "asc");

        return "index";
    }

    @GetMapping("/sort")
    public String getToDos(@RequestParam(value = "page", required = false) Optional<Integer> page,
                           @RequestParam( value = "sortField", required = false) Optional<String> sortField,
                           @RequestParam(value = "sortDir", required = false) Optional<String> sortDirection,
                           Model model){
        int currentPage = page.orElse(1);
        String currentSortField = sortField.orElse("");
        String currentSortDirec = sortDirection.orElse("asc");
        List<ToDoDto> toDoDtoList = toDoService.findAll(currentPage,currentSortField, currentSortDirec);
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
                                  Model model){
        int currentPage = page.orElse(1);
        if(!StringUtils.isEmptyOrWhitespace(title)){
            List<ToDoDto> toDoDtos = toDoService.findByTitle(title, currentPage);
            int totalPages = toDoService.totalPages();
            model.addAttribute("endPoint", "/searchByTitle");
            model.addAttribute("title", title);
            model.addAttribute("currentPage", currentPage);
            model.addAttribute("totalPages", totalPages);
            model.addAttribute("listToDos", toDoDtos);
            model.addAttribute("reverseSortDir",  "asc");
        } else {
            return "redirect:/index";
        }

        return "byTitle";
    }

    @GetMapping("/sort/searchByTitle")
    public String getToDosByTitleSort(@RequestParam(value = "page", required = false) Optional<Integer> page,
                                  @RequestParam(value = "title") String title,
                                  @RequestParam(value = "sortField") Optional<String> sortField,
                                  @RequestParam(value = "sortDir") Optional<String> sortDirection,
                                  Model model){
        int currentPage = page.orElse(1);
        String currentSortField = sortField.orElse("title");
        String currentSortDirec = sortDirection.orElse("asc");
        if(!StringUtils.isEmptyOrWhitespace(title) ){
            List<ToDoDto> toDoDtos = toDoService.findByTitle(title, currentPage, currentSortField, currentSortDirec);
            int totalPages = toDoService.totalPages();
            model.addAttribute("endPoint", "/sort/searchByTitle");
            model.addAttribute("title", title);
            model.addAttribute("currentPage", currentPage);
            model.addAttribute("totalPages", totalPages);
            model.addAttribute("listToDos", toDoDtos);

            model.addAttribute("sortField", currentSortField);
            model.addAttribute("sortDir", currentSortDirec);
            model.addAttribute("reverseSortDir", currentSortDirec.equals("asc") ? "desc" : "asc");
        } else {
            //model.addAttribute("listToDos", toDoService.findAll(currentPage));
            return "redirect:/index";
        }

        return "byTitle";
    }

    @GetMapping("/searchByUsername")
    public String getToDosByUsername(@RequestParam(value = "page") Optional<Integer> page,
                                     @RequestParam(value = "username") String username,
                                     Model model){
        int currentPage = page.orElse(1);
        if(!StringUtils.isEmptyOrWhitespace(username)){
            List<ToDoDto> toDoDtos = toDoService.findByUsername(username, currentPage);
            int totalPages = toDoService.totalPages();
            model.addAttribute("endPoint", "/searchByUsername");
            model.addAttribute("username", username);
            model.addAttribute("currentPage", currentPage);
            model.addAttribute("totalPages", totalPages);
            model.addAttribute("listToDos", toDoDtos);
            model.addAttribute("reverseSortDir",  "asc");
        } else {
            //model.addAttribute("listToDos", toDoService.findAll(currentPage));
            return "redirect:/index";
        }

        return "filterByUsername";
    }

    @GetMapping("/sort/searchByUsername")
    public String getToDosByUsernameSort(@RequestParam(value = "page") Optional<Integer> page,
                                     @RequestParam(value = "username") String username,
                                     @RequestParam(value = "sortField") Optional<String> sortField,
                                     @RequestParam(value = "sortDir") Optional<String> sortDirection,
                                     Model model){
        int currentPage = page.orElse(1);
        String currentSortField = sortField.orElse("");
        String currentSortDirec = sortDirection.orElse("asc");
        if(!StringUtils.isEmptyOrWhitespace(username)){
            List<ToDoDto> toDoDtos = toDoService.findByUsername(username, currentPage, currentSortField, currentSortDirec);
            int totalPages = toDoService.totalPages();
            model.addAttribute("endPoint", "/sort/searchByUsername");
            model.addAttribute("username", username);
            model.addAttribute("currentPage", currentPage);
            model.addAttribute("totalPages", totalPages);
            model.addAttribute("listToDos", toDoDtos);

            model.addAttribute("sortField", currentSortField);
            model.addAttribute("sortDir", currentSortDirec);
            model.addAttribute("reverseSortDir", currentSortDirec.equals("asc") ? "desc" : "asc");

        } else {
            //model.addAttribute("listToDos", toDoService.findAll(currentPage));
            return "redirect:/index";
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
    public String save(@Valid @ModelAttribute ToDo toDo, Model model){
        List<User> userList = userService.getAll();
        if(StringUtils.isEmptyOrWhitespace(toDo.getTitle())){
            model.addAttribute("title", "New ToDo");
            model.addAttribute("toDo", toDo);
            model.addAttribute("users", userList);
            model.addAttribute("error", "Title cannot be empty");
            return "formToDo";
        }

        if(toDo.getTitle().length()>200){
            model.addAttribute("title", "New ToDo");
            model.addAttribute("toDo", toDo);
            model.addAttribute("users", userList);
            model.addAttribute("error", "Max 200 characters");
            return "formToDo";
        }

        if(toDoService.existsByTitle(toDo.getTitle())){
            model.addAttribute("title", "New ToDo");
            model.addAttribute("toDo", toDo);
            model.addAttribute("users", userList);
            model.addAttribute("error", "Title already in use");
            return "formToDo";
        }

        toDoService.saveToDo(toDo);
        return "redirect:/index";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer idToDo, Model model){

        ToDo toDo = null;
        if(idToDo > 0){
            toDo = toDoService.findById(idToDo);
            if(toDo == null){
                System.out.println("Error, id doesn't exist");
                return "redirect:/index";
            }
        }else {
            System.out.println("Error, bad id");
            return "redirect:/index";
        }

        List<User> userList = userService.getAll();
        model.addAttribute("title", "Edit ToDo");
        model.addAttribute("toDo", toDo);
        model.addAttribute("users", userList);

        return "formToDo";

    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer idToDo){

        if(toDoService.existsById(idToDo)){
            toDoService.delete(idToDo);
            return "redirect:/index";
        }
        return "redirect:/index";
    }

}
