package com.WebGenerator.App.api.controller;


import com.WebGenerator.App.api.dto.WebSiteDto;
import com.WebGenerator.App.domain.service.IWebSiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/websites")
public class WebSiteController {
    @Autowired
    private IWebSiteService webSiteService;


    @GetMapping("/")
    public List<WebSiteDto> All(){
        return webSiteService.allWebSites();
    }
//
//    private WebSiteDto toDto(WebSite webSite){
//        return modelMapper.map(webSite, WebSiteDto.class);
//    }
//
    @PostMapping("/")
    public WebSiteDto create(@RequestBody WebSiteDto webSite){
        return webSiteService.create(webSite);
    }
}
