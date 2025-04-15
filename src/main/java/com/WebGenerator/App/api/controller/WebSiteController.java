package com.WebGenerator.App.api.controller;


import com.WebGenerator.App.api.dto.WebSiteDto;
import com.WebGenerator.App.domain.model.Img;
import com.WebGenerator.App.domain.model.WebSite;
import com.WebGenerator.App.domain.service.IWebSiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchTracksRequest;

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

    @PostMapping(path = "/add-img/{idWebSite}")
    public Img addImg(@PathVariable Long idWebSite, @RequestParam("file") MultipartFile file){
        WebSite webSite = webSiteService.getWebSiteById(idWebSite);
        return  webSiteService.addImg(webSite, file);
    }

    @GetMapping("/get-img/{idWebSite}")
    public List<Img> getImg(@PathVariable Long idWebSite){
        WebSite webSite = webSiteService.getWebSiteById(idWebSite);
        return webSite.getImgs();
    }

    @GetMapping("/list-music")
    public SearchTracksRequest searchMusic(@RequestParam String search){
        return webSiteService.listarMusicas(search);
    }
}
