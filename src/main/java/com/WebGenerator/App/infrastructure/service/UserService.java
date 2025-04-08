package com.WebGenerator.App.infrastructure.service;

import com.WebGenerator.App.api.controller.util.exception.UserNotFoundException;
import com.WebGenerator.App.api.dto.UserDto;
import com.WebGenerator.App.api.mapper.UserMapper;
import com.WebGenerator.App.domain.model.User;
import com.WebGenerator.App.domain.service.IUserService;
import com.WebGenerator.App.infrastructure.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public List<UserDto> getUsers() {
        return userRepository.allUsers()
                .stream()
                .map(userMapper::userModelToUserDto)
                .collect(Collectors.toList());
    }

    public UserDto getFirstUserByName(String name) {
         User user = userRepository.findFirstByName(name)
                .orElseThrow(UserNotFoundException::new);
         return userMapper.userModelToUserDto(user);
    }

    public UserDto getFirstUserById(Long id) {
       User user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
       return userMapper.userModelToUserDto(user);
    }

    public List<UserDto> getAllUsersSortedByNameAsc() {
        return userRepository.all(Sort.by(Sort.Direction.ASC, "name"))
                .stream()
                .map(userMapper::userModelToUserDto)
                .collect(Collectors.toList());
    }

    public UserDto create(UserDto userDto){
        User userSave = userMapper.userDtoToUserModel(userDto);
        userRepository.save(userSave);
        return userMapper.userModelToUserDto(userSave);
    }

    public List<UserDto> getAllUsersSorted(Sort sort) {
        return userRepository.all(sort)
                .stream()
                .map(userMapper::userModelToUserDto)
                .collect(Collectors.toList());
    }

    public void sendEmailUser(UserDto userDto){
        return;
    }
}
