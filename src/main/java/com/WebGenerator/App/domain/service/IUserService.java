package com.WebGenerator.App.domain.service;

import com.WebGenerator.App.api.dto.UserDto;
import com.WebGenerator.App.domain.localization.EmailTextProvider;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface IUserService {
    public List<UserDto> getUsers();
    public UserDto getFirstUserByName(String name);
    public UserDto getFirstUserById(Long id);
    public List<UserDto> getAllUsersSortedByNameAsc();
    public UserDto create(UserDto userDto);
    public List<UserDto> getAllUsersSorted(Sort sort);
}
