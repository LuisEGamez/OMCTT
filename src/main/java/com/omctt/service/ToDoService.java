package com.omctt.service;

import com.omctt.dto.ToDoDto;
import com.omctt.entity.ToDo;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ToDoService {

    Page<ToDoDto> findAll(int pageNo);

    List<ToDoDto> findAll1(int pageNo, String sortFilter, String sortDirection);


    Page<ToDoDto> findByTitle(String title, int pageNo);

    Page<ToDoDto> findByUsername(String username,int pageNo);

    void saveToDo(ToDo toDo);

    ToDo findById(Integer idToDo);

    void delete(Integer idToDo);

    int totalPages();

    //Page<ToDoDto> findPaginated(int pageNo, List<ToDoDto>toDoDtos);
}
