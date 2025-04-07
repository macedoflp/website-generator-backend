package com.WebGenerator.App.api.controller;


import com.WebGenerator.App.api.dto.WebSiteDto;
import com.WebGenerator.App.domain.models.WebSite;
import com.WebGenerator.App.api.service.WebSiteService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/websites")
public class WebSiteController {
    @Autowired
    private WebSiteService webSiteService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/")
    public List<WebSiteDto> All(){
        return webSiteService.allWebSites()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private WebSiteDto toDto(WebSite webSite){
        return modelMapper.map(webSite, WebSiteDto.class);
    }

    @PostMapping("/")
    public WebSiteDto create(@RequestBody WebSite webSite){
        WebSite webSiteModel =  webSiteService.create(webSite);
        return modelMapper.map(webSiteModel, WebSiteDto.class);
    }
}
