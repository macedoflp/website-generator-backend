package com.WebGenerator.App.api.controller;

import com.WebGenerator.App.api.dto.RegistrationDto;
import com.WebGenerator.App.api.dto.RegistrationResponseDto;
import com.WebGenerator.App.domain.localization.EmailTextProvider;
import com.WebGenerator.App.domain.model.QRCodeModel;
import com.WebGenerator.App.infrastructure.service.MailService;
import com.WebGenerator.App.infrastructure.service.RegistrationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/register")
public class RegistrationController {

    private Map<EmailTextProvider.Language, String> assunto;

    public RegistrationController(){
        this.assunto = new HashMap<>();
        assunto.put(EmailTextProvider.Language.EN, "Your website is ready");
        assunto.put(EmailTextProvider.Language.ES, "Tu sitio está listo");
        assunto.put(EmailTextProvider.Language.PT, "Seu site está pronto");
    }

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private MailService mailService;

    @PostMapping
    public ResponseEntity<?> register(
            @RequestBody @Valid RegistrationDto registrationDto,
            @RequestParam EmailTextProvider.Language language,
            @RequestParam QRCodeModel qrModel
    ){
        RegistrationResponseDto response = registrationService.registerUserWhiWebSite(registrationDto, language);

        if (response.webSite() != null) {
            mailService.sendEmail(
                    registrationDto.getUserDto().getEmail(),
                    assunto.get(language),
                    mailService.renderHtmlFromTemplate(response.webSite().getUrlWebSite(), language, qrModel)
            );
        }

        return ResponseEntity.ok(response);
    }

}
