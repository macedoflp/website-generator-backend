package com.WebGenerator.App.api.service;

import com.WebGenerator.App.domain.models.WebSite;
import com.WebGenerator.App.domain.repository.WebSiteRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WebSiteService {

    @Autowired
    private WebSiteRespository webSiteRespository;

    public WebSite create(WebSite webSite){
        return webSiteRespository.save(webSite);
    }

    public List<WebSite> allWebSites(){
        return webSiteRespository.findAll();
    }
}
