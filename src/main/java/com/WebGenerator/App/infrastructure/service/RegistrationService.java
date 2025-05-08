package com.WebGenerator.App.infrastructure.service;

import com.WebGenerator.App.api.controller.util.exception.CodeGenerator;
import com.WebGenerator.App.api.dto.*;
import com.WebGenerator.App.api.mapper.UserMapper;
import com.WebGenerator.App.api.mapper.WebSiteMapper;
import com.WebGenerator.App.config.SecurityConfiguration;
import com.WebGenerator.App.domain.localization.EmailTextProvider;
import com.WebGenerator.App.domain.model.User;
import com.WebGenerator.App.domain.model.WebSite;
import com.WebGenerator.App.infrastructure.repository.UserRepository;
import com.WebGenerator.App.infrastructure.repository.WebSiteRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * Responsavel pela criação conjunta de um User e um WebSite
 */
@Service
public class RegistrationService {

/*    @Autowired
    private UserMapper userMapper;

    @Autowired
    private WebSiteMapper webSiteMapper;

    @Autowired
    private WebSiteRespository webSiteRespository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecurityConfiguration securityConfiguration;

    @Autowired
    private MailService mailService;

    @Autowired
    private AutenticationService autenticationService;

    private Map<EmailTextProvider.Language, String> assunto;

    public RegistrationService(){
        this.assunto = new HashMap<>();
        assunto.put(EmailTextProvider.Language.EN, "Your verification code");
        assunto.put(EmailTextProvider.Language.ES, "Su código de verificación");
        assunto.put(EmailTextProvider.Language.PT, "Seu código de verificação");
    }*/

//    @Transactional
//    public RegistrationResponseDto registerUserWhiWebSite(RegistrationDto registrationDto, EmailTextProvider.Language language){
//
//        User userRecover = userRepository.findFirstByEmail(registrationDto.getUserDto().getEmail());
//        WebSite webSiteSave = webSiteMapper.webSiteDtoToWebSiteModel(registrationDto.getWebSiteDto());
//
////        User userSave = userMapper.userDtoToUserModel(registrationDto.getUserDto());
//
//        WebSite webSiteSaved = webSiteRespository.save(webSiteSave);
//        String linkWebSite = "https://love-timeline-five.vercel.app/" + webSiteSaved.getId() + "/" + slugify(webSiteSaved.getTitle());
//        webSiteSaved.setUrlWebSite(linkWebSite);
//        webSiteSaved = webSiteRespository.save(webSiteSaved);
//
//        if (userRecover != null){
//
//            String generatedCode = CodeGenerator.generateCode(6);
//            String encodeCode = securityConfiguration.passwordEncoder().encode(generatedCode);
//            userRecover.setGeneratedCode(encodeCode);
//            userRepository.save(userRecover);
//
//            // Vincula website ao usuário
//            webSiteSave.setUser(userRecover);
//            userRecover.getWebSites().add(webSiteSave);
//            webSiteRespository.save(webSiteSave);
//
//            // Envia email com código
//            mailService.sendEmail(
//                userRecover.getEmail(),
//                assunto.get(assunto),
//                mailService.renderHtmlFromTemplate(generatedCode, language)
//            );
//
//            LoginUserDto loginDto = new LoginUserDto(userRecover.getEmail(), generatedCode);
//            RecoveryJwtTokenDto token = autenticationService.authenticateUser(loginDto);
//
//            RegistrationResponseDto response = new RegistrationResponseDto(
//                    webSiteMapper.webSiteModelToWebSiteDto(webSiteSaved),
//                    token
//            );
//            return response;
//
//        }
//
//        // Usuário novo: criar user + site
//        User userSave = userMapper.userDtoToUserModel(registrationDto.getUserDto());
//        webSiteSave.setUser(userSave);
//        userSave.getWebSites().add(webSiteSave);
//
//        webSiteRespository.save(webSiteSave);
//        userRepository.save(userSave);
//
//        return new RegistrationResponseDto(
//                webSiteMapper.webSiteModelToWebSiteDto(webSiteSave),
//                null
//        );
//
//    }

    private String slugify(String text) {
        if (text == null || text.isEmpty()) {
            return "";
        }

        return text
                .trim() // Remove espaços do início e do fim
                .toLowerCase() // Converte para letras minúsculas
                .replaceAll("[\\u0300-\\u036f]", "") // Remove os acentos
                .replaceAll("&", "and") // Substitui "&" por "and"
                .replaceAll("[^a-z0-9]+", "-") // Substitui caracteres inválidos por hífens
                .replaceAll("^-+|-+$", ""); // Remove hífens do início e do fim
    }

}
