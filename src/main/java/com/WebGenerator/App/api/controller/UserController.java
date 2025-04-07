package com.WebGenerator.App.api.controller;

import com.WebGenerator.App.api.controller.util.exception.UserNotFoundException;
import com.WebGenerator.App.api.dto.UserDto;
import com.WebGenerator.App.domain.models.User;
import com.WebGenerator.App.api.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/by-name")
    public User getUserByName(@RequestParam String name) {
        return userService.getFirstUserByName(name).orElseThrow(UserNotFoundException::new);
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        User user = userService.getFirstUserById(id).orElseThrow(UserNotFoundException::new);
        return toDto(user);
    }

    private UserDto toDto(User user){
        return modelMapper.map(user, UserDto.class);
    }

    @GetMapping("/sorted")
    public List<User> getSortedUsers() {
        return userService.getAllUsersSortedByNameAsc();
    }

    @PostMapping("/")
    public UserDto create(@RequestBody User user){
        User userModel = userService.create(user);
        return modelMapper.map(userModel, UserDto.class);
    }

}