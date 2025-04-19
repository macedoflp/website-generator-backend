package com.WebGenerator.App.api.controller;

import com.WebGenerator.App.api.dto.UserDto;
import com.WebGenerator.App.domain.service.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping("/")
    public List<UserDto> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/by-name")
    public UserDto getUserByName(@RequestParam String name) {
        return userService.getFirstUserByName(name);
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        return userService.getFirstUserById(id);
    }

    @GetMapping("/sorted")
    public List<UserDto> getSortedUsers() {
        return userService.getAllUsersSortedByNameAsc();
    }

    @GetMapping("/sorted-by")
    public List<UserDto> getSortedUsersBy(@RequestParam String param) {
        Sort sort = Sort.by(Sort.Direction.ASC, param);
        return userService.getAllUsersSorted(sort);
    }

    @PostMapping("/")
    public UserDto create(@RequestBody @Valid UserDto userDto){
        return userService.create(userDto);
    }

}