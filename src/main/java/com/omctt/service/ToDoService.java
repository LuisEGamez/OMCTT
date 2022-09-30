package com.omctt.service;

import com.omctt.dto.ToDoDto;
import com.omctt.entity.ToDo;

import java.util.List;

public interface ToDoService {

    List<ToDoDto> findAll();

    void saveToDo(ToDo toDo);
}
