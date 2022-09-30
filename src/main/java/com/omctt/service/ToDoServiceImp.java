package com.omctt.service;

import com.omctt.dto.ToDoDto;
import com.omctt.entity.ToDo;
import com.omctt.repository.ToDoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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

    @Override
    public ToDo findById(Integer idToDo) {
        Optional<ToDo> optionalToDo = toDoRepository.findById(idToDo);
        ToDo toDo = null;
        if (optionalToDo.isPresent()){
            toDo = optionalToDo.get();
        }
        return toDo;
    }

    private ToDoDto convertToDto(ToDo toDo) {
        ToDoDto toDoDto = new ToDoDto();
        toDoDto.setTitle(toDo.getTitle());
        toDoDto.setCompleted(toDo.getCompleted());
        if(toDo.getUser()==null){
            toDoDto.setUserName("");
            toDoDto.setCountry("");
        }else {
            toDoDto.setUserName(toDo.getUser().getUsername());
            toDoDto.setCountry(toDo.getUser().getAddress().getCountry());
        }
        return toDoDto;
    }
}
