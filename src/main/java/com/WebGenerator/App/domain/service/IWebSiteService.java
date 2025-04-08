package com.WebGenerator.App.domain.service;

import com.WebGenerator.App.api.dto.WebSiteDto;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface IWebSiteService {
    public WebSiteDto create(WebSiteDto webSiteDto);
    public List<WebSiteDto> allWebSites();
    public List<WebSiteDto> allWebSitesSortedBy(Sort sort);
}
