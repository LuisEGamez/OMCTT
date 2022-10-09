package com.omctt.service;

import com.omctt.dto.ToDoDto;
import com.omctt.entity.Address;
import com.omctt.entity.ToDo;
import com.omctt.entity.User;
import com.omctt.repository.ToDoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

@ExtendWith(MockitoExtension.class)
public class ToDoServiceTest {

    @Mock
    private ToDoRepository toDoRepository;

    @Autowired
    @InjectMocks
    private ToDoServiceImp toDoService;

    private List<ToDo> toDoList;

    private Page<ToDo> toDoPage;

    private ToDo toDo;

    @BeforeEach
    void setUp() {
        Address address = new Address(1, "Lala", "BCN", "87654", "Spain", null);
        User user = new User(1, "Luis", "luis23", "123456", null, address);
        User user2 = new User(2, "Bea", "bea33", "123456", null, address);
        toDo = new ToDo(1, "TitleTest", true, user);
        ToDo toDo2 = new ToDo(2, "T1tleTest2", true, user2);
        toDoList = new ArrayList<>();
        toDoList.add(toDo);
        toDoList.add(toDo2);
        toDoPage = new PageImpl<>(toDoList);
        openMocks(this);

    }

    @Test
    void existsByTitleSuccess(){
        when(toDoService.existsByTitle(any(String.class))).thenReturn(true);

        boolean expected = toDoService.existsByTitle("Luis");

        assertThat(expected).isTrue();
    }

    @Test
    void existsByTitleFail(){
        when(toDoService.existsByTitle(any(String.class))).thenReturn(false);

        boolean expected = toDoService.existsByTitle("Luis");

        assertThat(expected).isFalse();
    }

    @Test
    void findAllTest(){
        when(toDoRepository.findAll(any(Pageable.class))).thenReturn(toDoPage);

        List<ToDoDto> expected = toDoService.findAll(1);

        assertThat(expected.size()).isEqualTo(toDoPage.getTotalElements());
        assertThat(expected.get(0).getTitle()).isEqualTo(toDoPage.stream().findFirst().get().getTitle());
    }

    @Test
    void findAllSortTest(){
        when(toDoRepository.findAll(any(Pageable.class))).thenReturn(toDoPage);

        List<ToDoDto> expected = toDoService.findAll(1, "title", "desc");

        assertThat(expected.size()).isEqualTo(toDoPage.getTotalElements());
        assertThat(expected.get(0).getTitle()).isEqualTo(toDoPage.stream().findFirst().get().getTitle());
    }

    @Test
    void findAllSortTest2(){
        when(toDoRepository.findAll(any(Pageable.class))).thenReturn(toDoPage);

        List<ToDoDto> expected = toDoService.findAll(1, "title", "asc");

        long count = toDoPage.stream().count();

        assertThat(expected.size()).isEqualTo(toDoPage.getTotalElements());
        assertThat(expected.get(1).getTitle()).isEqualTo(toDoPage.stream().skip(count - 1).findFirst().get().getTitle());
    }

    @Test
    void findByTitleTest(){
        when(toDoRepository.findByTitleContainingIgnoreCase(any(String.class), any(Pageable.class))).thenReturn(toDoList);

        List<ToDoDto> expected = toDoService.findByTitle("title", 1);

        assertThat(expected.size()).isEqualTo(toDoList.size());
        assertThat(expected.get(0).getTitle()).isEqualTo(toDoList.get(0).getTitle());
    }

    @Test
    void findByTitleSortTest(){
        when(toDoRepository.findByTitleContainingIgnoreCase(any(String.class), any(Pageable.class))).thenReturn(toDoList);

        List<ToDoDto> expected = toDoService.findByTitle("title", 1, "title", "asc");

        assertThat(expected.size()).isEqualTo(toDoList.size());
        assertThat(expected.get(0).getTitle()).isEqualTo(toDoList.get(0).getTitle());
    }

    @Test
    void findByUsernameTest(){
        when(toDoRepository.findByUsername(any(String.class), any(Pageable.class))).thenReturn(toDoList);

        List<ToDoDto> expected = toDoService.findByUsername("luis23", 1);

        assertThat(expected.size()).isEqualTo(toDoList.size());
        assertThat(expected.get(0).getTitle()).isEqualTo(toDoList.get(0).getTitle());
    }

    @Test
    void findByUsernameSortTest(){
        when(toDoRepository.findByUsername(any(String.class), any(Pageable.class))).thenReturn(toDoList);

        List<ToDoDto> expected = toDoService.findByUsername("luis23", 1, "title", "asc");

        assertThat(expected.size()).isEqualTo(toDoList.size());
        assertThat(expected.get(0).getTitle()).isEqualTo(toDoList.get(0).getTitle());
    }

    @Test
    void findByIdTest(){
        when(toDoRepository.findById(any(Integer.class))).thenReturn(Optional.ofNullable(toDo));

        ToDo expected = toDoService.findById(1);

        assertThat(expected.getId()).isEqualTo(toDo.getId());
    }
}
