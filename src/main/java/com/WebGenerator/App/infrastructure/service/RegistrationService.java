package com.WebGenerator.App.infrastructure.service;

import com.WebGenerator.App.api.dto.RegistrationDto;
import com.WebGenerator.App.api.dto.WebSiteDto;
import com.WebGenerator.App.api.mapper.UserMapper;
import com.WebGenerator.App.api.mapper.WebSiteMapper;
import com.WebGenerator.App.domain.model.User;
import com.WebGenerator.App.domain.model.WebSite;
import com.WebGenerator.App.infrastructure.repository.UserRepository;
import com.WebGenerator.App.infrastructure.repository.WebSiteRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Responsavel pela criação conjunta de um User e um WebSite
 */
@Service
public class RegistrationService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private WebSiteMapper webSiteMapper;

    @Autowired
    private WebSiteRespository webSiteRespository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public WebSiteDto registerUserWhiWebSite(RegistrationDto registrationDto){

        User userRecover = userRepository.findFirstByEmail(registrationDto.getUserDto().getEmail());

        User userSave = userMapper.userDtoToUserModel(registrationDto.getUserDto());
        WebSite webSiteSave = webSiteMapper.webSiteDtoToWebSiteModel(registrationDto.getWebSiteDto());

        WebSite webSiteSaved = webSiteRespository.save(webSiteSave);
        String linkWebSite = "https://love-timeline-five.vercel.app/" + webSiteSaved.getId() + "/" + slugify(webSiteSaved.getTitle());
        webSiteSaved.setUrlWebSite(linkWebSite);

        webSiteSaved = webSiteRespository.save(webSiteSaved);

        if (userRecover != null){
            webSiteSave.setUser(userRecover);
            userRecover.getWebSites().add(webSiteSave);
            webSiteSaved = webSiteRespository.save(webSiteSaved);
            return webSiteMapper.webSiteModelToWebSiteDto(webSiteSaved);
        }

        webSiteSave.setUser(userSave);
        userSave.getWebSites().add(webSiteSave);

        WebSite webSite = webSiteRespository.save(webSiteSave);
        User user = userRepository.save(userSave);

        return webSiteMapper.webSiteModelToWebSiteDto(webSite);

    }

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
