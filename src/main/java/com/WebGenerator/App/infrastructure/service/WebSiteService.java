package com.WebGenerator.App.infrastructure.service;

import com.WebGenerator.App.api.dto.WebSiteDto;
import com.WebGenerator.App.api.mapper.WebSiteMapper;
import com.WebGenerator.App.domain.model.WebSite;
import com.WebGenerator.App.domain.service.IWebSiteService;
import com.WebGenerator.App.infrastructure.repository.WebSiteRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WebSiteService implements IWebSiteService {

    @Autowired
    private WebSiteRespository webSiteRespository;

    @Autowired
    private WebSiteMapper webSiteMapper;

    public WebSiteDto create(WebSiteDto webSiteDto){
        WebSite webSiteSave = webSiteMapper.webSiteDtoToWebSiteModel(webSiteDto);
        webSiteRespository.save(webSiteSave);
        return webSiteMapper.webSiteModelToWebSiteDto(webSiteSave);
    }

    public List<WebSiteDto> allWebSites(){
        return webSiteRespository.findAll()
                .stream()
                .map(webSiteMapper::webSiteModelToWebSiteDto)
                .collect(Collectors.toList());
    }
}
