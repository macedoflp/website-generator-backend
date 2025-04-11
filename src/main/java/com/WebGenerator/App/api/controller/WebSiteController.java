package com.WebGenerator.App.api.controller;


import com.WebGenerator.App.api.dto.WebSiteDto;
import com.WebGenerator.App.domain.model.Img;
import com.WebGenerator.App.domain.model.WebSite;
import com.WebGenerator.App.domain.service.IWebSiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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

    @GetMapping("/sorted-by")
    public List<WebSiteDto> All(@RequestParam String param){
        Sort sort = Sort.by(Sort.Direction.ASC, param);
        return webSiteService.allWebSitesSortedBy(sort);
    }

    @PostMapping("/")
    public WebSiteDto create(@RequestBody WebSiteDto webSite){
        return webSiteService.create(webSite);
    }

    @PostMapping("/add-img/{id}")
    public Img addImg(@PathVariable Long id, @RequestBody Img img){
        WebSite webSite = webSiteService.getWebSiteById(id);
        return  webSiteService.addImg(webSite, img);
    }

}
