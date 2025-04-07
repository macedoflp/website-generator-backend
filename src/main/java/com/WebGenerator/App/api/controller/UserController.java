package com.WebGenerator.App.api.controller;

//import com.WebGenerator.App.api.controller.util.exception.UserNotFoundException;
import com.WebGenerator.App.api.dto.UserDto;
//import com.WebGenerator.App.domain.model.User;
import com.WebGenerator.App.domain.service.IUserService;
import com.WebGenerator.App.infrastructure.service.UserService;
//import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
//
//    @GetMapping("/{id}")
//    public UserDto getUserById(@PathVariable Long id) {
//        User user = userService.getFirstUserById(id).orElseThrow(UserNotFoundException::new);
//        return toDto(user);
//    }
//
//    private UserDto toDto(User user){
//        return modelMapper.map(user, UserDto.class);
//    }

//    @GetMapping("/sorted")
//    public List<User> getSortedUsers() {
//        return userService.getAllUsersSortedByNameAsc();
//    }

    @PostMapping("/")
    public UserDto create(@RequestBody UserDto userDto){
        return userService.create(userDto);
    }

}