package com.omctt.service;

import com.omctt.dto.ToDoDto;
import com.omctt.entity.ToDo;

import java.util.List;

public interface ToDoService {

    boolean existsByTitle(String title);

    boolean existsById(Integer id);

    List<ToDoDto> findAll(int pageNo);

    List<ToDoDto> findAll(int pageNo, String sortField, String sortDirection);


    List<ToDoDto> findByTitle(String title, int pageNo);

    List<ToDoDto> findByTitle(String title, int pageNo, String sortField, String sortDirection);

    List<ToDoDto> findByUsername(String username,int pageNo);

    List<ToDoDto> findByUsername(String username,int pageNo, String sortField, String sortDirection);

    void saveToDo(ToDo toDo);

    ToDo findById(Integer idToDo);

    void delete(Integer idToDo);

}
