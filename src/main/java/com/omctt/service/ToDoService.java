package com.omctt.service;

import com.omctt.dto.ToDoDto;

import java.util.List;

public interface ToDoService {

    List<ToDoDto> findAll();
}
