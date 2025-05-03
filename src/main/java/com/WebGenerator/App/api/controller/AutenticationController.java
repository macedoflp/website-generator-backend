package com.WebGenerator.App.api.controller;

import com.WebGenerator.App.api.dto.EmailDto;
import com.WebGenerator.App.api.dto.LoginUserDto;
import com.WebGenerator.App.api.dto.RecoveryJwtTokenDto;
import com.WebGenerator.App.domain.localization.EmailTextProvider;
import com.WebGenerator.App.infrastructure.service.AutenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class AutenticationController {

    @Autowired
    private AutenticationService autenticationService;

    @PostMapping("/validate-code")
    public ResponseEntity<RecoveryJwtTokenDto> authenticateUser(@RequestBody LoginUserDto loginUserDto) {
        RecoveryJwtTokenDto token = autenticationService.authenticateUser(loginUserDto);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping("/request-code")
    public ResponseEntity<Void> requestCode(@RequestBody EmailDto emailDto, @RequestParam EmailTextProvider.Language language) {
        autenticationService.sendLoginCode(emailDto.email(), language);
        return ResponseEntity.ok().build();
    }

}
