package com.omctt.service;

import com.omctt.dto.ToDoDto;
import com.omctt.entity.ToDo;
import com.omctt.repository.ToDoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ToDoServiceImp implements ToDoService{

    private final int PAGE_SIDE = 10;

    @Autowired
    private ToDoRepository toDoRepository;

    @Override
    public Page<ToDoDto> findAll(int pageNo) {
        List<ToDoDto> toDoDtos = toDoRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .toList();
        return pager(pageNo, toDoDtos);
    }

    @Override
    public Page<ToDoDto> findByTitle(String title, int pageNo) {
        List<ToDoDto> toDoDtos = toDoRepository.findByTitleContainingIgnoreCase(title)
                .stream()
                .map(this::convertToDto)
                .toList();
        return pager(pageNo, toDoDtos);
    }

    @Override
    public Page<ToDoDto> findByUsername(String username, int pageNo) {
        List<ToDoDto> toDoDtos = toDoRepository.findByUsername(username)
                .stream()
                .map(this::convertToDto)
                .toList();
        return pager(pageNo, toDoDtos);
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

    @Override
    public void delete(Integer idToDo) {
        toDoRepository.deleteById(idToDo);
    }


    private Page<ToDoDto> pager(int pageNo, List<ToDoDto> toDoDtos) {
        Pageable pageable = PageRequest.of(pageNo - 1, PAGE_SIDE);
        //List<ToDoDto> all = toDoRepository.findAll().stream().map(this::convertToDto).toList();
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), toDoDtos.size());
        return new PageImpl<>(toDoDtos.subList(start,end), pageable, toDoDtos.size());

    }

    private ToDoDto convertToDto(ToDo toDo) {
        ToDoDto toDoDto = new ToDoDto();
        toDoDto.setId(toDo.getId());
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
