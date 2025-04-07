package com.WebGenerator.App.api.mapper;

import com.WebGenerator.App.api.dto.UserDto;
import com.WebGenerator.App.domain.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto userModelToUserDto(User user);
    User userDtoToUserModel(UserDto userDto);
}
