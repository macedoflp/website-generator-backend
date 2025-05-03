package com.WebGenerator.App.api.controller;

import com.WebGenerator.App.api.dto.UserDto;
import com.WebGenerator.App.domain.localization.EmailTextProvider;
import com.WebGenerator.App.domain.service.IUserService;
import com.WebGenerator.App.infrastructure.service.util.exception.UserAlreadyExistsException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<?> create(@RequestBody UserDto user){
        try {
            UserDto createdUser = userService.create(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (UserAlreadyExistsException ex) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", ex.getMessage());
            response.put("id", ex.getUserId());
            response.put("email", ex.getEmail());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
    }

}