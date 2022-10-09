package com.omctt.service;

import com.omctt.dto.ToDoDto;
import com.omctt.entity.ToDo;
import com.omctt.repository.ToDoRepository;
import com.omctt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ToDoServiceImp implements ToDoService{

    private final int PAGE_SIDE = 10;

    @Autowired
    private ToDoRepository toDoRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public int totalPages() {
        return (int) Math.ceil(toDoRepository.count()/10.0) ;
    }

    @Override
    public boolean existsByTitle(String title) {
        return toDoRepository.existsByTitle(title);
    }

    @Override
    public boolean existsById(Integer id) {
        return toDoRepository.existsById(id);
    }

    @Override
    public List<ToDoDto> findAll(int pageNo) {
        Pageable pageable = PageRequest.of(pageNo - 1, PAGE_SIDE);
        return toDoRepository.findAll(pageable)
                .stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    public List<ToDoDto> findAll(int pageNo, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, PAGE_SIDE, sort);

        return toDoRepository.findAll(pageable)
                .stream()
                .map(this::convertToDto)
                .toList();

    }


    @Override
    public List<ToDoDto> findByTitle(String title, int pageNo) {
        Pageable pageable = PageRequest.of(pageNo - 1, PAGE_SIDE);
        return toDoRepository.findByTitleContainingIgnoreCase(title, pageable)
                .stream()
                .map(this::convertToDto)
                .toList();

    }

    @Override
    public List<ToDoDto> findByTitle(String title, int pageNo, String sortField, String sortDirection) {

        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, PAGE_SIDE, sort);

        return toDoRepository.findByTitleContainingIgnoreCase(title, pageable)
                .stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    public List<ToDoDto> findByUsername(String username, int pageNo) {
        Pageable pageable = PageRequest.of(pageNo - 1, PAGE_SIDE);
        return toDoRepository.findByUsername(username, pageable)
                .stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    public List<ToDoDto> findByUsername(String username, int pageNo, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, PAGE_SIDE, sort);
        return toDoRepository.findByUsername(username, pageable)
                .stream()
                .map(this::convertToDto)
                .toList();
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
