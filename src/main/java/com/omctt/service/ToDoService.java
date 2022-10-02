package com.omctt.service;

import com.omctt.dto.ToDoDto;
import com.omctt.entity.ToDo;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ToDoService {

    List<ToDoDto> findAll();

    List<ToDoDto> findByTitle(String title);

    List<ToDoDto> findByUsername(String username);

    void saveToDo(ToDo toDo);

    ToDo findById(Integer idToDo);

    void delete(Integer idToDo);

    Page<ToDoDto> findPaginated(int pageNo);
}
