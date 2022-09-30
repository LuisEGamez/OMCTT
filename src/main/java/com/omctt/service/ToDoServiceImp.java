package com.omctt.service;

import com.omctt.dto.ToDoDto;
import com.omctt.entity.ToDo;
import com.omctt.repository.ToDoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ToDoServiceImp implements ToDoService{

    @Autowired
    private ToDoRepository toDoRepository;

    @Override
    public List<ToDoDto> findAll() {
        return toDoRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void saveToDo(ToDo toDo) {
        toDoRepository.save(toDo);
    }

    private ToDoDto convertToDto(ToDo toDo) {
        return new ToDoDto(
                toDo.getTitle(),
                toDo.getUser().getUsername(),
                toDo.getUser().getAddress().getCountry(),
                toDo.getCompleted()
        );
    }
}
