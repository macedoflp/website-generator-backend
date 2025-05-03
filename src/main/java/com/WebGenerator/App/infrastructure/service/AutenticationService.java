package com.WebGenerator.App.infrastructure.service;

import com.WebGenerator.App.api.controller.util.exception.CodeGenerator;
import com.WebGenerator.App.api.dto.LoginUserDto;
import com.WebGenerator.App.api.dto.RecoveryJwtTokenDto;
import com.WebGenerator.App.config.SecurityConfiguration;
import com.WebGenerator.App.domain.localization.EmailTextProvider;
import com.WebGenerator.App.domain.model.User;
import com.WebGenerator.App.infrastructure.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class AutenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private SecurityConfiguration securityConfiguration;

    @Autowired
    private MailService mailService;

    @Autowired
    private UserRepository userRepository;

    private Map<EmailTextProvider.Language, String> assunto;

    public AutenticationService(){
        this.assunto = new HashMap<>();
        assunto.put(EmailTextProvider.Language.EN, "Your verification code");
        assunto.put(EmailTextProvider.Language.ES, "Su código de verificación");
        assunto.put(EmailTextProvider.Language.PT, "Seu código de verificação");
    }

    public RecoveryJwtTokenDto authenticateUser(LoginUserDto login){

        User user = userRepository.findFirstByEmail(login.email());

        if (user == null) {
            throw new UsernameNotFoundException("Usuário não encontrado");
        }

        if (user.getCodeExpiration() == null || user.getCodeExpiration().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Código expirado.");
        }

        if (!securityConfiguration.passwordEncoder().matches(login.generatedCode(), user.getTemporaryCode())) {
            throw new BadCredentialsException("Código inválido");
        }


        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(login.email(), login.generatedCode());


        Authentication authentication =  authenticationManager.authenticate(
                usernamePasswordAuthenticationToken
        );

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return new RecoveryJwtTokenDto(jwtTokenService.generateToken(userDetails));
    }


    public void sendLoginCode(String email, EmailTextProvider.Language language) {
        User user = userRepository.findFirstByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("Usuário não encontrado");
        }

        String code = CodeGenerator.generateCode(6);
        user.setTemporaryCode(securityConfiguration.passwordEncoder().encode(code));
        user.setCodeExpiration(LocalDateTime.now().plusMinutes(10)); // expira em 10 min
        userRepository.save(user);

        mailService.sendEmail(
            email,
            assunto.get(language),
            mailService.renderHtmlFromTemplate(code, language)
        );
    }

}
