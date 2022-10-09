package com.omctt.service;

import com.omctt.dto.ToDoDto;
import com.omctt.entity.Address;
import com.omctt.entity.ToDo;
import com.omctt.entity.User;
import com.omctt.repository.ToDoRepository;
import com.omctt.repository.UserRepository;
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
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Autowired
    @InjectMocks
    private UserServiceImp userServiceImp;

    private List<User> userList;

    private User user;

    @BeforeEach
    void setUp() {
        Address address = new Address(1, "Lala", "BCN", "87654", "Spain", null);
        user = new User(1, "Luis", "luis23", "123456", null, address);
        User user2 = new User(2, "Bea", "bea33", "123456", null, address);
        userList = new ArrayList<>();
        userList.add(user);
        userList.add(user2);
        openMocks(this);

    }

    @Test
    void existsById(){
        when(userRepository.existsById(any(Integer.class))).thenReturn(true);

        boolean expected = userServiceImp.existsById(1);

        assertThat(expected).isTrue();
    }

    @Test
    void existsByUsername(){
        when(userRepository.existsByUsername(any(String.class))).thenReturn(true);

        boolean expected = userServiceImp.existsByUser("luis23");

        assertThat(expected).isTrue();
    }

    @Test
    void findAllTest(){
        when(userRepository.findAll()).thenReturn(userList);

        List<User> expected = userServiceImp.getAll();

        assertThat(expected.size()).isEqualTo(userList.size());
        assertThat(expected.get(0).getUsername()).isEqualTo(userList.get(0).getUsername());
    }

    @Test
    void findByUsernameTest(){
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.ofNullable(user));

        Optional<User> expected = userServiceImp.findByUsername("luis23");

        assertThat(expected.get().getUsername()).isEqualTo(user.getUsername());

    }

    @Test
    void findByIdTest(){
        when(userRepository.findById(any(Integer.class))).thenReturn(Optional.ofNullable(user));

        Optional<User> expected = userServiceImp.findById(1);

        assertThat(expected.get().getId()).isEqualTo(user.getId());

    }

}
