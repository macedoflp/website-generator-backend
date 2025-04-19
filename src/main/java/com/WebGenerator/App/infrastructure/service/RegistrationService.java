package com.WebGenerator.App.infrastructure.service;

import com.WebGenerator.App.api.dto.RegistrationDto;
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
    public boolean registerUserWhiWebSite(RegistrationDto registrationDto){

        User userRecover = userRepository.findFirstByEmail(registrationDto.getUserDto().getEmail());

        User userSave = userMapper.userDtoToUserModel(registrationDto.getUserDto());
        WebSite webSiteSave = webSiteMapper.webSiteDtoToWebSiteModel(registrationDto.getWebSiteDto());

        if (userRecover != null){
            webSiteSave.setUser(userRecover);
            userRecover.getWebSites().add(webSiteSave);
            webSiteRespository.save(webSiteSave);
            return true;
        }

        webSiteSave.setUser(userSave);
        userSave.getWebSites().add(webSiteSave);

        WebSite webSite = webSiteRespository.save(webSiteSave);
        User user = userRepository.save(userSave);

        return webSite.getId() != null && user.getId() != null;

    }

}
