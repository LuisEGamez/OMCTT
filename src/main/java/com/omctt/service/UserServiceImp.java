package com.omctt.service;

import com.omctt.entity.User;
import com.omctt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImp implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findById(Integer id) {
        User user = null;
        Optional<User> userOptional = userRepository.findById(id);

        if(userOptional.isPresent()){
            user = userOptional.get();
        }

        return user;
    }
}
