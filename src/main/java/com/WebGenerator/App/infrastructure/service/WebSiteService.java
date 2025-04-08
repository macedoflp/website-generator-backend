package com.WebGenerator.App.infrastructure.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.WebGenerator.App.api.dto.WebSiteDto;
import com.WebGenerator.App.api.mapper.WebSiteMapper;
import com.WebGenerator.App.domain.model.WebSite;
import com.WebGenerator.App.domain.service.IWebSiteService;
import com.WebGenerator.App.infrastructure.repository.WebSiteRespository;

@Service
public class WebSiteService implements IWebSiteService {

    @Autowired
    private WebSiteRespository webSiteRespository;

    @Autowired
    private WebSiteMapper webSiteMapper;

    @Override
    public WebSiteDto create(WebSiteDto webSiteDto){
        WebSite webSiteSave = webSiteMapper.webSiteDtoToWebSiteModel(webSiteDto);
        webSiteRespository.save(webSiteSave);
        return webSiteMapper.webSiteModelToWebSiteDto(webSiteSave);
    }

    @Override
    public List<WebSiteDto> allWebSites(){
        return webSiteRespository.findAll()
                .stream()
                .map(webSiteMapper::webSiteModelToWebSiteDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<WebSiteDto> allWebSitesSortedBy(Sort sort){
        return webSiteRespository.findAll(sort)
                .stream()
                .map(webSiteMapper::webSiteModelToWebSiteDto)
                .collect(Collectors.toList());
    }
}
