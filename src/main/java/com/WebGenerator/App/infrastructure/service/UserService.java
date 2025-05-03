package com.WebGenerator.App.infrastructure.service;

import com.WebGenerator.App.api.controller.util.exception.CodeGenerator;
import com.WebGenerator.App.api.controller.util.exception.UserNotFoundException;
import com.WebGenerator.App.api.dto.UserDto;
import com.WebGenerator.App.api.mapper.UserMapper;
import com.WebGenerator.App.config.SecurityConfiguration;
import com.WebGenerator.App.domain.localization.EmailTextProvider;
import com.WebGenerator.App.domain.model.Role;
import com.WebGenerator.App.domain.model.User;
import com.WebGenerator.App.domain.service.IUserService;
import com.WebGenerator.App.infrastructure.repository.RoleRepository;
import com.WebGenerator.App.infrastructure.repository.UserRepository;
import com.WebGenerator.App.infrastructure.service.util.exception.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {

    private Map<EmailTextProvider.Language, String> assunto;

    public UserService(){
        this.assunto = new HashMap<>();
        assunto.put(EmailTextProvider.Language.EN, "Your verification code");
        assunto.put(EmailTextProvider.Language.ES, "Su código de verificación");
        assunto.put(EmailTextProvider.Language.PT, "Seu código de verificação");
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private MailService mailService;

    @Autowired
    private SecurityConfiguration securityConfiguration;

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
        // recuperar o usuario
        User userRecover = userRepository.findFirstByEmail(userDto.getEmail());

        if (userRecover != null){

            throw new UserAlreadyExistsException(
                    "Usuário já existe. Um novo código de verificação foi enviado para o e-mail.",
                    userRecover.getId(),
                    userRecover.getEmail()
            );
        }

        Role roleRecovered = roleRepository
                .findByName(userDto.getRole())
                .orElseThrow(() -> new RuntimeException("Role não encontrada"));

        User userSave = userMapper.userDtoToUserModel(userDto);
        userSave.getRoles().add(roleRecovered);

        userRepository.save(userSave);
        return userMapper.userModelToUserDto(userSave);
    }

    public List<UserDto> getAllUsersSorted(Sort sort) {
        return userRepository.all(sort)
                .stream()
                .map(userMapper::userModelToUserDto)
                .collect(Collectors.toList());
    }


}
