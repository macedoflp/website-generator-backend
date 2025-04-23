package com.WebGenerator.App.api.controller;

import com.WebGenerator.App.api.dto.LoginUserDto;
import com.WebGenerator.App.api.dto.RecoveryJwtTokenDto;
import com.WebGenerator.App.infrastructure.service.AutenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticationController {

    @Autowired
    private AutenticationService autenticationService;

    @PostMapping("/")
    public ResponseEntity<RecoveryJwtTokenDto> authenticateUser(@RequestBody LoginUserDto loginUserDto) {
        System.err.println("Bateu na rota de autenticação: " + loginUserDto.generatedCode());
        RecoveryJwtTokenDto token = autenticationService.authenticateUser(loginUserDto);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

}
