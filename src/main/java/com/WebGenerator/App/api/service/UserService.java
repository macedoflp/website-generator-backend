package com.WebGenerator.App.api.service;

import com.WebGenerator.App.domain.models.User;
import com.WebGenerator.App.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getUsers() {
        return userRepository.allUsers();
    }

    public Optional<User> getFirstUserByName(String name) {
        return userRepository.findFirstByName(name);
    }

    public Optional<User> getFirstUserById(Long id) {
        return userRepository.findById(id);
    }

    public List<User> getAllUsersSortedByNameAsc() {
        return userRepository.all(Sort.by(Sort.Direction.ASC, "name"));
    }


    public User create(User user){
        return userRepository.save(user);
    }

    public List<User> getAllUsersSorted(Sort sort) {
        return userRepository.all(sort);
    }

    public void sendEmailUser(User user){
        return;
    }
}
